import model.MovimentacaoFinanceira;
import service.LeitorCSV;
import service.Processador;
import util.FormatarValor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.DoubleSummaryStatistics;

public class Main {

    public static void main(String[] args) {
        LeitorCSV leitorCSV = new LeitorCSV();
        Processador processador = new Processador();

        try {
            /* Usando o File diretamente */
            File file = new File("src/resources/financas_pessoais.csv");

            /* Verifica se o arquivo existe */
            if (!file.exists()) {
                System.out.println("Arquivo não encontrado: " + file.getPath());
                return;
            }

            /* Leitura do arquivo CSV */
            List<MovimentacaoFinanceira> movimentacoes = leitorCSV.lerArquivo(file);

            /* Processamento das movimentações */
            Optional<MovimentacaoFinanceira> maior = processador.encontrarMaiorMovimentacao(movimentacoes);
            Optional<MovimentacaoFinanceira> menor = processador.encontrarMenorMovimentacao(movimentacoes);
            DoubleSummaryStatistics estatisticas = processador.calcularEstatisticas(movimentacoes);
            List<MovimentacaoFinanceira> grandesMovimentacoes = processador.filtrarPorValorMinimo(movimentacoes, 1000.00);

            /* Exibição dos resultados */
            System.out.println("Maior movimentação: " + maior.orElse(null));
            System.out.println("Menor movimentação: " + menor.orElse(null));
            System.out.println("Média das movimentações: " + FormatarValor.formatarMoeda(estatisticas.getAverage()));
            System.out.println("Movimentações acima de R$1000,00:");
            grandesMovimentacoes.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
