package service;

import model.MovimentacaoFinanceira;
import translator.TradutorCSV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LeitorCSV {

    public List<MovimentacaoFinanceira> lerArquivo(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines()
                    .skip(1) /* Pula a linha do cabeçalho */
                    .map(TradutorCSV::traduzirLinha) /* Converte cada linha do CSV em um objeto MovimentacaoFinanceira */
                    .collect(Collectors.toList());
        }
    }
}
