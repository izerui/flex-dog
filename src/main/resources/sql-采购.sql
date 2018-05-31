SELECT
	d.*,
	c.purge_qty as c_purge_qty
FROM
	mrp.demand_result d
left join (
SELECT
	ent_code,
	business_source_id as business_key,
	inventory_id,
	SUM(
		(quantity - total_inbound_qty) + (
			CASE
			WHEN exchange_qty IS NOT NULL THEN
				exchange_qty
			ELSE
				0
			END - CASE
			WHEN exchange_inbound_qty IS NOT NULL THEN
				exchange_inbound_qty
			ELSE
				0
			END
		)
	) as purge_qty
FROM
	purchase.purchase_demand
WHERE
  attribute_code = ? and
	doc_status = 'UNFINISHED'
AND business_source_id IS NOT NULL
AND business_source_id != ''
GROUP BY
  ent_code,
	business_source_id,
	inventory_id
) as c using(ent_code,business_key,inventory_id)
WHERE
	d.attribute_code = ?
	and d.purge_qty <> 0
	and (c.purge_qty is null or d.purge_qty <> c.purge_qty)
  and d.ent_code = ?