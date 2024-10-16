package service;

import model.MovimentacaoFinanceira;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Processador {

    public Optional<MovimentacaoFinanceira> encontrarMaiorMovimentacao(List<MovimentacaoFinanceira> movimentacoes) {
        return movimentacoes.stream()
                .reduce((m1, m2) -> m1.getValor() > m2.getValor() ? m1 : m2);
    }

    public Optional<MovimentacaoFinanceira> encontrarMenorMovimentacao(List<MovimentacaoFinanceira> movimentacoes) {
        return movimentacoes.stream()
                .reduce((m1, m2) -> m1.getValor() < m2.getValor() ? m1 : m2);
    }

    public DoubleSummaryStatistics calcularEstatisticas(List<MovimentacaoFinanceira> movimentacoes) {
        return movimentacoes.stream()
                .mapToDouble(MovimentacaoFinanceira::getValor)
                .summaryStatistics(); // Retorna várias estatísticas (média, mínimo, máximo)
    }

    public List<MovimentacaoFinanceira> filtrarPorValorMinimo(List<MovimentacaoFinanceira> movimentacoes, double valorMinimo) {
        return movimentacoes.stream()
                .filter(m -> m.getValor() > valorMinimo)
                .collect(Collectors.toList());
    }
}
