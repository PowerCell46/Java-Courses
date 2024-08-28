SELECT
    (SELECT name FROM customers c1 WHERE c1.id = s.customer_id) AS full_name,
    (SELECT COUNT(*) FROM sales s2 WHERE s2.customer_id = s.customer_id) AS bought_cars,
    (
    SELECT
        SUM(
            (SELECT
                 (
                    SELECT
                        SUM(price)
                    FROM
                        cars c3
                    JOIN parts_cars pc3 ON
                            c3.id = pc3.car_id
                    JOIN parts p3 ON
                            pc3.part_id = p3.id
                    WHERE
                        c3.id = s4.car_id
                 ) / 100 * (100 - s4.discount)
            )
        ) AS total_discounted_sum
    FROM
        sales s4
    WHERE
        s4.customer_id = s.customer_id
    ) AS spent_money
FROM
    sales s
GROUP BY
    s.customer_id
ORDER BY
    spent_money DESC,
    bought_cars DESC;


-- # SELECT
-- #     (SELECT
-- #          (
-- #             SELECT
-- #                 SUM(price)
-- #             FROM
-- #                 cars c3
-- #             JOIN parts_cars pc3 ON
-- #                     c3.id = pc3.car_id
-- #             JOIN parts p3 ON
-- #                     pc3.part_id = p3.id
-- #             WHERE
-- #                 c3.id = s4.car_id
-- #          ) / 100 * (100 - s4.discount)
-- #     ) AS discounted_sum
-- # FROM
-- #     sales s4
-- # WHERE
-- #     s4.customer_id = 14