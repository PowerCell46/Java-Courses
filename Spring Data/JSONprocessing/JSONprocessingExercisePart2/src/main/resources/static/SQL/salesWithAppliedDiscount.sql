SELECT

    (
        SELECT
            name
        FROM
            customers c1
        JOIN sales s1 ON
            c1.id = s1.customer_id
        WHERE
            s1.id = s.id
    ) AS name,

    (
        SELECT
            s2.discount
        FROM
            sales s2
        WHERE
            s2.id = s.id
    ) AS discount,

    (
        SELECT
            SUM(p3.price)
        FROM
            sales s3
        JOIN cars c3 ON
                s3.car_id = c3.id
        JOIN parts_cars pc3 ON
            c3.id = pc3.car_id
        JOIN parts p3 ON
            pc3.part_id = p3.id
        WHERE
            s3.id = s.id
        ) AS price,

    (
        SELECT ( (
            SELECT
                SUM(p3.price)
            FROM
                sales s3
            JOIN cars c3 ON
                    s3.car_id = c3.id
            JOIN parts_cars pc3 ON
                c3.id = pc3.car_id
            JOIN parts p3 ON
                pc3.part_id = p3.id
            WHERE
                s3.id = s.id
            ) / 100) * (100 - (
                    SELECT
                        s2.discount
                    FROM
                        sales s2
                    WHERE
                        s2.id = s.id
                )
            )
    ) AS price_with_discount
FROM
    sales s
GROUP BY
    s.id;