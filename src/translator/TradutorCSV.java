package translator;

import model.MovimentacaoFinanceira;

public class TradutorCSV {

    public static MovimentacaoFinanceira traduzirLinha(String linha) {
        String[] campos = linha.split(",");

        /* Remover aspas dos campos conforme necessário */
        String data = campos[0].replace("\"", "").trim();
        String descricao = campos[1].replace("\"", "").trim();
        double valor = Double.parseDouble(campos[2].replace("\"", "").trim());

        return new MovimentacaoFinanceira(DataUtil.parseData(data), descricao, valor);
    }
}
