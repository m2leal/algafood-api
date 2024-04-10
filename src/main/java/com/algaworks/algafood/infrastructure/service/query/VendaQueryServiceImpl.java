package com.algaworks.algafood.infrastructure.service.query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {
	
	@PersistenceContext
	private EntityManager manager;
	
//	Versão pra banco H2
	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT CAST(DATEADD(HOUR,");
		sb.append(timeOffset.split(":")[0].replace("+", ""));
		sb.append(", p.dataCriacao) AS date) AS data, COUNT(p.id), SUM(p.valorTotal) FROM Pedido p WHERE 1=1");
		
		if (filtro.getRestauranteId() != null) {
			sb.append(" AND restaurante_id = " + filtro.getRestauranteId());
		}
	
		if (filtro.getDataCriacaoInicio() != null) {
			sb.append(" AND dataCriacao >= '" + filtro.getDataCriacaoInicio().toString().replace("T", " ").replace("Z", "'"));
		}
		
		if (filtro.getDataCriacaoFim() != null) {
			sb.append(" AND dataCriacao <= '" + filtro.getDataCriacaoFim().toString().replace("T", " ").replace("Z", "'"));
		}
	
		sb.append(" AND status IN ('CONFIRMADO','ENTREGUE')");
		sb.append(" GROUP BY data");
		
		List<Object[]> resultados = manager.createQuery(sb.toString(), Object[].class).getResultList();
		
		List<VendaDiaria> vendasDiarias = new ArrayList<>();

		for (Object[] linha : resultados) {
			VendaDiaria venda = new VendaDiaria(
				(Date) linha[0],
				(Long) linha[1],
				(BigDecimal) linha[2]);
			vendasDiarias.add(venda);
		}
		
		return vendasDiarias;
	}
	
//	Versão pra banco MySQL
//	@Override
//	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
//		var builder = manager.getCriteriaBuilder();
//		var query = builder.createQuery(VendaDiaria.class);
//		var root = query.from(Pedido.class);
//		var predicates = new ArrayList<Predicate>();
//		
//		var functionConvertTzDataCriacao = builder.function(
//				"convert_tz", Date.class, root.get("dataCriacao"),
//				builder.literal("+00:00"), builder.literal(timeOffset));
//		
//		var functionDateDataCriacao = builder.function(
//				"date", Date.class, functionConvertTzDataCriacao);
//		
//		var selection = builder.construct(VendaDiaria.class,
//				functionDateDataCriacao,
//				builder.count(root.get("id")),
//				builder.sum(root.get("valorTotal")));
//		
//		if (filtro.getRestauranteId() != null) {
//			predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
//		}
//		
//		if (filtro.getDataCriacaoInicio() != null) {
//			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), 
//					filtro.getDataCriacaoInicio()));
//		}
//		
//		if (filtro.getDataCriacaoFim() != null) {
//			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), 
//					filtro.getDataCriacaoFim()));
//		}
//		
//		predicates.add(root.get("status").in(
//				StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
//		
//		query.select(selection);
//		query.where(predicates.toArray(new Predicate[0]));
//		query.groupBy(functionDateDataCriacao);
//		
//		return manager.createQuery(query).getResultList();
//	}
	
}
