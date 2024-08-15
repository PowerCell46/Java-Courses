package bg.softuni.orm;

import bg.softuni.orm.annotations.Column;
import bg.softuni.orm.annotations.Entity;
import bg.softuni.orm.annotations.Id;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
public class EntityManager<T> implements DatabaseContext<T> {

    private static final String ALTER_TABLE_TEMPLATE = "" +
            "ALTER TABLE " +
            "   %s " +
            "ADD COLUMN " +
            "   %s";

    private static final String GET_EXISTING_COLUMNS_TEMPLATE = "" +
                "SELECT " +
                "   COLUMN_NAME " +
                "FROM " +
                "   information_schema.COLUMNS " +
                "WHERE " +
                "   TABLE_SCHEMA = 'mini_orm' " +
                "AND " +
                "   TABLE_NAME = ?";

    private Connection connection;
    private static final String SQL_INSERT_TEMPLATE = "" +
        "INSERT INTO " +
        "   %s (%s) " +
        "VALUES " +
        "   (%s);" +
        "";

    private static final String SQL_UPDATE_TEMPLATE = "" +
        "UPDATE " +
        "   %s " +
        "SET " +
        "   %s " +
        "WHERE " +
        "   %s";

    private static final String SQL_SELECT_TEMPLATE = "" +
            "SELECT " +
            "   %s " +
            "FROM " +
            "   %s " +
            "WHERE " +
            "   %s " +
            "   %s ";

    private static final String SQL_CREATE_TEMPLATE = "" +
            "CREATE TABLE " +
            "   %s(" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "%s)";

    private static final String SQL_DELETE_TEMPLATE = "" +
            "DELETE " +
            "FROM " +
            "   %s " +
            "WHERE " +
            "   id = ?";

    @Override
    public void doCreate(Class<T> entityClass) throws SQLException {
        String sql = String.format(SQL_CREATE_TEMPLATE, getTableName(entityClass), getAllFieldsAndDatatypes(entityClass));

        boolean execute = connection.prepareStatement(sql).execute();
    }

    @Override
    public boolean delete(T entity) throws SQLException, IllegalAccessException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(
            String.format(SQL_DELETE_TEMPLATE, getTableName(entity))
        );

        Field fieldId = getIdColumn(entity);
        fieldId.setAccessible(true);

        preparedStatement.setLong(1, fieldId.getLong(entity));

        return preparedStatement.executeUpdate() != 0;
    }

    @Override
    public void doAlter(T entity) throws SQLException {
        String newColumns = getColumnsNotExistingInTable(entity);
        String sql = String.format(ALTER_TABLE_TEMPLATE, getTableName(entity), newColumns);
        this.connection.prepareStatement(sql).execute();
    }

    private String getColumnsNotExistingInTable(T entity) throws SQLException {
        List<String> existingColumns = getExistingColumns(entity);
        return Arrays.stream(entity.getClass().getDeclaredFields())
            .filter(f -> !existingColumns.contains(f.getAnnotation(Column.class).name()))
            .map(f -> String.format("%s %s", getFieldName(f), getFieldType(f)))
            .collect(Collectors.joining(", "));
    }

    private List<String> getExistingColumns(T entity) throws SQLException {
        List<String> existingColumns = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(GET_EXISTING_COLUMNS_TEMPLATE);
        preparedStatement.setString(1, getTableName(entity));
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            existingColumns.add(resultSet.getString(1));
        }

        return existingColumns;
    }

    private String getAllFieldsAndDatatypes(Class<T> entityClass) {
        List<String> fields = new ArrayList<>();
        Field[] declaredFields = entityClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) continue;
            fields.add(String.format("%s %s", getFieldName(declaredField), getFieldType(declaredField)));
        }

        return String.join(", ", fields);
    }

    private Object getFieldType(Field declaredField) {
        return switch (declaredField.getType().getSimpleName()) {
            case "int", "Integer" -> "INT";
            case "long", "Long" -> "LONG";
            case "String" -> "VARCHAR(255)";
            case "double", "Double" -> "DOUBLE";
            case "LocalDate" -> "DATE";
            default -> null;
        };
    }

    private String getFieldName(Field declaredField) {
        return declaredField.getAnnotation(Column.class).name();
    }

    @Override
    public boolean persist(T entity) throws SQLException, IllegalAccessException {
        Field idColumn = getIdColumn(entity);
        idColumn.setAccessible(true);
        Object idValue = idColumn.get(entity);

        if (idValue == null || (long) idValue == 0) { // no Id -> create
            return doInsert(entity);

        } else { // Id -> update
            return doUpdate(entity, idColumn, idValue);
        }
    }

    private boolean doUpdate(T entity, Field idColumn, Object idValue) throws IllegalAccessException, SQLException {
        List<String> columnsWithoutId = getColumnNamesWithoutId(entity);
        List<String> columnValuesWithoutId = getColumnValuesWithoutId(entity);

        List<String> columnsWithValues = new ArrayList<>();

        for (int i = 0; i < columnsWithoutId.size(); i++) {
            columnsWithValues.add(String.format(
                "%s = %s",
                columnsWithoutId.get(i),
                columnValuesWithoutId.get(i)
            ));
        }

        String sql = String.format(
            SQL_UPDATE_TEMPLATE,
            getTableName(entity),
            String.join(", ", columnsWithValues),
            String.format("%s = %s", idColumn.getName(), idValue.toString())
        );

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int updateCount = preparedStatement.executeUpdate();

        return updateCount == 1;
    }

    private boolean doInsert(T entity) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity);
        List<String> columnsList = getColumnNamesWithoutId(entity);
        List<String> values = getColumnValuesWithoutId(entity);

        String sql = String.format(
            SQL_INSERT_TEMPLATE,
            tableName,
            String.join(", ", columnsList),
            String.join(", ", values)
        );

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int changedRows = preparedStatement.executeUpdate();

        return changedRows == 1;
    }


    @Override
    public Iterable<T> find(Class<T> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return this.find(table, null, false);
    }

    @Override
    public Iterable<T> find(Class<T> table, String where, boolean limitOne) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String columnNames = "*";
        String tableName = getTableName(table);
        where = where == null ? "1 = 1" : where;
        String limit = limitOne ? "LIMIT 1" : "";

        String sql = String.format(SQL_SELECT_TEMPLATE, columnNames, tableName, where, limit);

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<T> result = new ArrayList<>();

        while (resultSet.next()) {
            T current = generateEntity(table, resultSet);
            result.add(current);
        }

        return result;
    }

    private T generateEntity(Class<T> table, ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        T result = table.getDeclaredConstructor().newInstance();

        Field[] declaredFields = table.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            fillField(result, declaredField, resultSet);
        }

        return result;
    }

    private void fillField(T result, Field declaredField, ResultSet resultSet) throws SQLException, IllegalAccessException {
        declaredField.setAccessible(true);

        String columnName = declaredField.getName();
        Class<?> fieldType = declaredField.getType();

        if (fieldType == int.class || fieldType == Integer.class) {
            declaredField.set(result, resultSet.getInt(columnName));
        } else if (fieldType == long.class || fieldType == Long.class) {
            declaredField.set(result, resultSet.getLong(columnName));
        } else if (fieldType == double.class || fieldType == Double.class) {
            declaredField.set(result, resultSet.getDouble(columnName));
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            declaredField.set(result, resultSet.getBoolean(columnName));
        } else if (fieldType == String.class) {
            declaredField.set(result, resultSet.getString(columnName));
        } else if (fieldType == LocalDate.class) { // TODO: hardcoded so it could work...
            java.sql.Date sqlDate = resultSet.getDate("registration_timestamp");
            if (sqlDate != null) {
                declaredField.set(result, sqlDate.toLocalDate());
            } else {
                declaredField.set(result, null);
            }
        } else if (fieldType == Timestamp.class) {
            declaredField.set(result, resultSet.getTimestamp(columnName));
        } else if (fieldType == float.class || fieldType == Float.class) {
            declaredField.set(result, resultSet.getFloat(columnName));
        } else if (fieldType == short.class || fieldType == Short.class) {
            declaredField.set(result, resultSet.getShort(columnName));
        } else if (fieldType == byte.class || fieldType == Byte.class) {
            declaredField.set(result, resultSet.getByte(columnName));
        } else if (fieldType == BigDecimal.class) {
            declaredField.set(result, resultSet.getBigDecimal(columnName));
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType.getName());
        }
    }

    @Override
    public T findFirst(Class<T> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return this.find(table, null, true).iterator().next();
    }

    @Override
    public T findFirst(Class<T> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return this.find(table, where, true).iterator().next();
    }


    private String getTableName(T entity) {
        Entity annotation = entity.getClass().getAnnotation(Entity.class);

        if (annotation == null) {
            throw new RuntimeException("No entity annotation present");
        }

        return annotation.name();
    }

    private String getTableName(Class<T> clazz) {
        Entity annotation = clazz.getAnnotation(Entity.class);

        if (annotation == null) {
            throw new RuntimeException("No entity annotation present");
        }

        return annotation.name();
    }

    private List<String> getColumnNamesWithoutId(T entity) {
        List<String> result = new ArrayList<>();
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                continue;
            }

            Column column = declaredField.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }

            result.add(column.name());
        }
        
        return result;
    }

    private List<String> getColumnValuesWithoutId(T entity) throws IllegalAccessException {
        List<String> result = new ArrayList<>();
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                continue;
            }

            Column column = declaredField.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }

            declaredField.setAccessible(true);
            result.add("'" + declaredField.get(entity).toString() + "'");
        }

        return result;
    }

    private Field getIdColumn(T entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                return declaredField;
            }
        }
        throw new RuntimeException("Entity has no ID column");
    }
}
