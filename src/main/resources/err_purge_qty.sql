SELECT
	d.ent_code ,
	d.attribute_code,
	d.business_doc_no,
	d.business_key ,
	d.inventory_id ,
	d.inventory_code,
	d.inventory_name,
	d.unit_name,
	d.demand_qty,
	d.purge_qty ,
	c.purge_qty AS c_purge_qty
FROM
	mrp.demand_result d
LEFT JOIN(
	SELECT
		ent_code ,
		business_key ,
		inventory_id ,
		sum(purge_qty) AS purge_qty
	FROM
		(
			SELECT
				*
			FROM
				purchase.demand
			UNION
				SELECT
					*
				FROM
					purchase.demand_hh
				UNION
					SELECT
						*
					FROM
						purchase.demand_ww
					UNION
						SELECT
							*
						FROM
							manufacture.demand
						UNION
							SELECT
								*
							FROM
								warehouse.demand
		) AS dm
	GROUP BY
		ent_code ,
		business_key ,
		inventory_id
) AS c USING(
	ent_code ,
	business_key ,
	inventory_id
)
WHERE
	d.purge_qty <> 0
AND(
	c.purge_qty IS NULL
	OR d.purge_qty <> c.purge_qty
)
and ent_code = ?