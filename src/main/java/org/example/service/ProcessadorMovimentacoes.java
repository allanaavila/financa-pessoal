package org.example.service;

import org.example.model.MovimentacaoFinanceira;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcessadorMovimentacoes implements Processador<MovimentacaoFinanceira> {

    private List<MovimentacaoFinanceira> movimentacoes;

    public ProcessadorMovimentacoes(List<MovimentacaoFinanceira> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }


    @Override
    public List<MovimentacaoFinanceira> filtrarPorCategoria(String categoria) {
        return movimentacoes.stream()
                .filter(m -> m.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

   /* @Override
    public List<MovimentacaoFinanceira> filtrarRecorrentes() {
<<<<<<< HEAD

=======
        
>>>>>>> 5b565de7d4dce4b92df4adb47cdae19a57ce0a72
        Map<String, List<MovimentacaoFinanceira>> mapaRecorrencias = movimentacoes.stream()
                .collect(Collectors.groupingBy(mov -> mov.getDescricao() + "|" + mov.getValor()));

z
        return mapaRecorrencias.values().stream()
                .filter(lista -> lista.size() > 1)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }*/
   public Map<String, Long> filtrarRecorrentes() {

       // Agrupa as movimentações por descrição e valor e conta as ocorrências
       return movimentacoes.stream()
               .collect(Collectors.groupingBy(
                       mov -> mov.getDescricao() + " - Valor: " + mov.getValor(), // Chave: descrição e valor
                       Collectors.counting() // Valor: quantidade de vezes que aparece
               ))
               .entrySet().stream()
               .filter(entry -> entry.getValue() > 1) // Filtra as que se repetem
               .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); // Retorna o mapa final
   }


    @Override
    public BigDecimal calcularTotalDeGasto() {
        return movimentacoes.stream()
                .map(MovimentacaoFinanceira::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Map<String, BigDecimal> calcularTotalPorCategoria() {
        return movimentacoes.stream()
                .collect(Collectors.groupingBy(MovimentacaoFinanceira::getCategoria,
                        Collectors.mapping(MovimentacaoFinanceira::getValor,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    @Override
    public Map<String, BigDecimal> calcularTotalPorTipoPagamento() {
        return movimentacoes.stream()
                .collect(Collectors.groupingBy(MovimentacaoFinanceira::getTipoPagamento,
                        Collectors.mapping(MovimentacaoFinanceira::getValor,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    @Override
    public MovimentacaoFinanceira encontrarMaiorMovimentacao() {
        return movimentacoes.stream()
                .max(Comparator.comparing(MovimentacaoFinanceira::getValor))
                .orElse(null);
    }

    @Override
    public BigDecimal calcularMediaDeGastos() {
        return calcularTotalDeGasto().divide(BigDecimal.valueOf(movimentacoes.size()),
                BigDecimal.ROUND_HALF_UP);
    }
}
