SELECT
	d.* ,
	c.purge_qty AS c_purge_qty
FROM
	mrp.demand_result d
LEFT JOIN(
	SELECT
		ent_code,
		business_key ,
		inventory_id ,
		sum(quantity - total_inbound_quantity) as purge_qty
	FROM
		warehouse.wait_receive
	WHERE
		attribute_code = ? and
		doc_type = '4' and record_status = 1
	GROUP BY
	  ent_code,
		business_key ,
		inventory_id
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