SELECT
	d.* ,
	c.purge_qty AS c_purge_qty
FROM
	mrp.demand_result d
LEFT JOIN(
	SELECT
		ent_code ,
		business_key ,
		inventory_id ,
		sum(
			quantity - total_redirect_qty - total_inbound_qty
		) as purge_qty
	FROM
		manufacture.production_demand
	WHERE
		attribute_code = ?
	GROUP BY
		ent_code ,
		inventory_id ,
		business_key
) AS c USING(
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