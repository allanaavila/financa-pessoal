package model;

import java.time.LocalDate;

public class MovimentacaoFinanceira {
    private LocalDate data;
    private String descricao;
    private double valor;

    public MovimentacaoFinanceira(LocalDate data, String descricao, double valor) {
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Data: " + data + ", Descrição: " + descricao + ", Valor: " + valor;
    }
}
