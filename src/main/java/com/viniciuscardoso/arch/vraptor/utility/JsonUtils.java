package com.viniciuscardoso.arch.vraptor.utility;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.viniciuscardoso.arch.vraptor.controller.json.JqGrid;
import com.viniciuscardoso.arch.vraptor.controller.json.JqGridRow;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Project: transforma-siblend
 * User: Vinicius
 * Date: 25/07/14
 * Time: 14:58
 */
public class JsonUtils {

    /**
     * Retorna JSON contendo status 200
     * @param result Objeto result do Vraptor
     */
    public static void setSuccessJsonResult(Result result) {
        result.use(Results.status()).ok();
    }

	/**
	 * Retorna JSON contendo status 200 e mensagem de sucesso
	 * @param result Objeto result do Vraptor
	 * @param message Mensagem de sucesso para exibir ao usuário
     */
	public static void setSuccessJsonResult(Result result, String message) {
		result.use(Results.json()).withoutRoot().from(message).serialize();
	}

    /**
	 * Retorna JSON contendo dados de erro, para exibição ao usuário
	 * @param result Objeto result do Vraptor
	 * @param e Exceção lançada
	 */
	public static void setErrorJsonResult(Result result, final Throwable e) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		String stackTrace = sw.getBuffer().toString();

		result.use(Results.http()).body(e.getMessage() + ":::" + stackTrace).setStatusCode(500);
	}

	/**
	 * Retorna JSON contendo dados de erro, para exibição ao usuário
	 * @param result Objeto result do Vraptor
	 * @param e Exceção lançada
	 */
	public static void setErrorJsonResultWithoutStacktrace(Result result, String title, final Throwable e) {
		result.use(Results.http()).body(title + ":::" + e.getMessage()).setStatusCode(500);
	}

	/**
	 * Retorna o json formatado para o JqGrid contendo dados para montar grid
	 * @param linhas Registros da consulta
	 * @param rows número de linhas selecionadas para exibir no grid
	 * @param page página atual (contada a partir de 1)
	 * @param result Objeto result do Vraptor
	 */
	public static void setJqGridJsonResult(ArrayList<JqGridRow> linhas, int rows, int page, Result result) {
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

    /**
     * Retorna o json formatado para o JqGrid contendo dados para montar grid, mas sem paginação, que é feita via sql,
     * por razões de performance
     * @param linhas Registros da consulta
     * @param rows número de linhas selecionadas para exibir no grid
     * @param page página atual (contada a partir de 1)
     * @param result Objeto result do Vraptor
     */
    public static void setJqGridJsonResultNoPagination(ArrayList<JqGridRow> linhas, int rows, int page, int total, Result result) {
        JqGrid j = new JqGrid();
        j.setPage(String.valueOf(page));
        j.setRecords(String.valueOf(total));
        j.setRows(linhas);
        j.setTotal(((Math.ceil(total / rows) == 0) ? 1 : ((int) Math.ceil((double) total / rows))));

        result.use(Results.json()).withoutRoot().from(j).recursive().serialize();
    }
}