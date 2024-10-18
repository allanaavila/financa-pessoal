package org.example;

import org.example.model.MovimentacaoFinanceira;
import org.example.service.ProcessadorMovimentacoes;
import org.example.service.LeitorCSV;
import org.example.translator.TradutorCSV;
import org.example.util.FormatarValor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        // Tradução e leitura dos dados
        TradutorCSV tradutor = new TradutorCSV();
        tradutor.traduzirArquivo();

        LeitorCSV leitor = new LeitorCSV("financas_pessoais.csv");
        List<MovimentacaoFinanceira> movimentacoes = leitor.lerMovimentacoes();
        ProcessadorMovimentacoes processador = new ProcessadorMovimentacoes(movimentacoes);

        // Exibição do total de gastos
        BigDecimal totalGastos = processador.calcularTotalDeGasto();
        System.out.println("\n================= RESUMO FINANCEIRO =================\n");
        System.out.println("🔸 Total de Gastos: " + FormatarValor.formatarValor(totalGastos));
        System.out.println("----------------------------------------------------");

        // Exibição da média de gastos
        BigDecimal mediaGastos = processador.calcularMediaDeGastos();
        System.out.println("📊 Média de Gastos: " + FormatarValor.formatarValor(mediaGastos));
        System.out.println("----------------------------------------------------");

        // Exibição da maior movimentação
        MovimentacaoFinanceira maiorMovimentacao = processador.encontrarMaiorMovimentacao();
        if (maiorMovimentacao != null) {
            System.out.println("💰 Maior Movimentação: ");
            System.out.println("   Descrição: " + maiorMovimentacao.getDescricao());
            System.out.println("   Valor: " + FormatarValor.formatarValor(maiorMovimentacao.getValor()));
        } else {
            System.out.println("Nenhuma movimentação encontrada.");
        }
        System.out.println("----------------------------------------------------");

        // Exibição do total por categoria (em formato de tabela)
        System.out.println("📂 Total por Categoria:");
        System.out.printf("%-20s | %15s\n", "Categoria", "Total");
        System.out.println("---------------------|-----------------");
        processador.calcularTotalPorCategoria().forEach((categoria, total) ->
                System.out.printf("%-20s | %15s\n", categoria, FormatarValor.formatarValor(total)));
        System.out.println("----------------------------------------------------");

        // Exibição do total por tipo de pagamento (em formato de tabela)
        System.out.println("💳 Total por Tipo de Pagamento:");
        System.out.printf("%-20s | %15s\n", "Tipo de Pagamento", "Total");
        System.out.println("---------------------|-----------------");
        processador.calcularTotalPorTipoPagamento().forEach((tipoPagamento, total) ->
                System.out.printf("%-20s | %15s\n", tipoPagamento, FormatarValor.formatarValor(total)));
        System.out.println("----------------------------------------------------");

        // Exibição das movimentações recorrentes
        System.out.println("🔄 Movimentações Recorrentes:");
        Map<String, Long> recorrentes = processador.filtrarRecorrentes();
        if (!recorrentes.isEmpty()) {
            System.out.printf("%-40s | %10s\n", "Movimentação", "Repetições");
            System.out.println("----------------------------------------|------------");
            recorrentes.forEach((descricao, count) ->
                    System.out.printf("%-40s | %10d\n", descricao, count));
        } else {
            System.out.println("Nenhuma movimentação recorrente encontrada.");
        }

        System.out.println("\n================= FIM DO RELATÓRIO =================");
    }
}
