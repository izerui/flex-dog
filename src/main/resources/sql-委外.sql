SELECT
	d.* ,
	c.purge_qty AS c_purge_qty
FROM
	mrp.demand_result d
LEFT JOIN
(
	SELECT
		ent_code ,
		business_source_id as business_key,
		inventory_id ,
		SUM(
			quantity - total_inbound_qty - CASE
			WHEN scrap_qty IS NOT NULL THEN
				scrap_qty
			ELSE
				0
			END
		) as purge_qty
	FROM
		purchase.outsource_demand
	WHERE
		attribute_code = ? and
		doc_status = 'UNFINISHED'
	GROUP BY
		business_source_id ,
		inventory_id
)
AS c USING(
	ent_code ,
	business_key ,
	inventory_id
)
WHERE
	d.attribute_code = ?
and d.purge_qty <> 0
AND(
	c.purge_qty IS NULL
	OR d.purge_qty <> c.purge_qty
)
and d.ent_code = ?