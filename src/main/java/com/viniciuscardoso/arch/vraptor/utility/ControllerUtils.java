package com.viniciuscardoso.arch.vraptor.utility;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.viniciuscardoso.arch.vraptor.controller.json.JqGrid;
import com.viniciuscardoso.arch.vraptor.controller.json.JqGridRow;
import org.joda.time.LocalDate;

import java.util.ArrayList;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 16:02
 */
public class ControllerUtils {

    /**
     * Configura mensagem de sucesso de uma requisição
     * @param result Objeto Result do Vraptor
     * @param objectActionText Texto contendo o nome do objeto mais o verbo correspondente à ação que obteve sucesso. Ex.: "Cliente salvo" ou "Produto excluído" etc.
     */
    public static void defineSuccessMessage(Result result, String objectActionText) {
        result.include("alerta", objectActionText + " com sucesso!");
        result.include("classeAlerta", "alert-success");
    }
    
    /**
     * Configura mensagem de sucesso de uma requisição
     * @param result Objeto Result do Vraptor
     * @param objectActionText Texto contendo o nome do objeto mais o verbo correspondente à ação que obteve sucesso. Ex.: "Cliente salvo" ou "Produto excluído" etc.
     * @param addSuccessfully Variável informando se é necessário complementar mensagem (adicionar 'com sucesso!' ao final)
     */
    public static void defineSuccessMessage(Result result, String objectActionText, boolean addSuccessfully) {
        if (addSuccessfully) {
            result.include("alerta", objectActionText + " com sucesso!");
        } else {
            result.include("alerta", objectActionText);
        }
        result.include("classeAlerta", "alert-success");
    }
    
    /**
     * Configura mensagem de sucesso de uma requisição, informando uma ação posterior a ser tomada.
     * @param result Objeto Result do Vraptor
     * @param objectActionText Texto contendo o nome do objeto mais o verbo correspondente à ação que obteve sucesso. Ex: "Cliente salvo" ou "Produto excluído" etc.
     * @param furtherAction Ação posterior a ser tomada. Ex: "Efetue um logout para aplicar as alterações."
     */
    public static void defineSuccessMessage(Result result, String objectActionText, String furtherAction) {
        result.include("alerta", objectActionText + " com sucesso! " + furtherAction);
        result.include("classeAlerta", "alert-success");
    }

    /**
     * Configura mensagem de erro de uma requisição
     * @param result Obsjeto result do Vraptor
     * @param e Exception lançada
     */
    public static void defineErrorMessage(Result result, Exception e) {
        result.include("alerta", e.getMessage());
        result.include("causa", " Causa: " + e.getCause());
        result.include("classeAlerta", "alert-danger");
    }
    
    /**
     * Configura mensagem de erro de uma requisição
     * @param result Obsjeto result do Vraptor
     * @param msg String com mensagem de erro
     */
    public static void defineErrorMessage(Result result, String msg) {
        result.include("alerta", msg);
        result.include("classeAlerta", "alert-danger");
    }

    /**
     * Configura mensagem de warning de uma requisição
     * @param result Obsjeto result do Vraptor
     * @param e Exception lançada
     * @param msg String com mensagem de warning
     */
    public static void defineWarningMessage(Result result, String msg) {
        result.include("alerta", msg);
        result.include("classeAlerta", "alert-warning");
    }
    
    /**
     * Define no result duas datas para uso por um seletor de período,
     * sendo que a data1 é agora, e a data2 é agora menos 3 meses.
     * @param result Objeto result do Vraptor
     */
    public static void defineTodayMinusThreeMonths(Result result, String pattern) {
        result.include("data1", new LocalDate().toString(pattern));
        result.include("data2", new LocalDate().minusMonths(3).toString(pattern));
    }
    
    /**
     * Define no result duas datas para uso por um seletor de período,
     * sendo que a data1 é o primeiro dia do ano atual, e a data2 é o último dia
     * do mês atual.
     * @param result Objeto result do Vraptor
     */
    public static void defineFromStartOfCurrentYearToLastDayCurrentMonth(Result result, String pattern) {
        result.include("data1", new LocalDate().dayOfYear().withMinimumValue().toString(pattern));
        result.include("data2", new LocalDate().dayOfMonth().withMaximumValue().toString(pattern));
    }
    
    /**
     * Define no result duas datas para uso por um seletor de período,
     * sendo que a data1 é o primeiro dia do mês atual, e a data2 é o último.
     * @param result Objeto result do Vraptor
     */
    public static void defineCurrentMonthFirstAndLastDays(Result result, String pattern) {
        result.include("data1", new LocalDate().dayOfMonth().withMinimumValue().toString(pattern));
        result.include("data2", new LocalDate().dayOfMonth().withMaximumValue().toString(pattern));
    }

    /**
     * Retorna o json formatado para o JqGrid contendo dados para montar grid
     * @param linhas Registros da consulta
     * @param rows número de linhas selecionadas para exibir no grid
     * @param page página atual (contada a partir de 1)
     * @param result Objeto result do Vraptor
     */
	@Deprecated
    public static void gerarJqGridJsonResult(ArrayList<JqGridRow> linhas, int rows, int page, Result result) {
        JqGrid j = new JqGrid();
        int inicioLista, fimLista;
        inicioLista = rows * (page - 1);
        fimLista = ((rows * page) > linhas.size()) ? linhas.size() : rows * page;

        j.setPage(String.valueOf(page));
        j.setRecords(String.valueOf(linhas.size()));
        j.setRows(new ArrayList<>(linhas.subList(inicioLista, fimLista)));
        j.setTotal(((Math.ceil(linhas.size() / rows) == 0) ? 1 : ((int) Math.ceil((double) linhas.size() / rows))));

        result.use(Results.json()).withoutRoot().from(j).recursive().serialize();
    }
}
