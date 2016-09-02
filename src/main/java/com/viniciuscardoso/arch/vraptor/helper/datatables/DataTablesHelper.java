package com.viniciuscardoso.arch.vraptor.helper.datatables;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Vinícius on 22/09/2015.
 */
public class DataTablesHelper<T> {

    private final Class clazz;

    public DataTablesHelper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    public DataTables<T> getDatatablesFromRawObjectArray(List<Object[]> rawObjectArray, boolean nullSearch, int start,
                                                         int length, int totalRegisters)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        List<Object[]> filteredList;
        if (length == -1) { //se length == -1, traz todos os registros.
            filteredList = rawObjectArray;
        } else {
            filteredList = rawObjectArray.subList(start, (start + length > rawObjectArray.size() ? rawObjectArray.size() : start + length));
        }
        DataTables<T> dataTables = new DataTables<>();
        dataTables.setRecordsTotal(totalRegisters);
        dataTables.setRecordsFiltered(nullSearch ? totalRegisters : rawObjectArray.size());

        for (Object[] rawObjects : filteredList) {
            dataTables.getData().add((T) this.clazz.getDeclaredConstructor(Object[].class).newInstance(new Object[]{rawObjects}));
        }
        return dataTables;
    }

    @SuppressWarnings("unchecked")
    public DataTables<T> getDatatablesFromRawObjectArray(List<Object[]> rawObjectArray, String search, int start, int length, int totalRegisters)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        List<Object[]> filteredList;
        if (length == -1) { //se length == -1, traz todos os registros.
            filteredList = rawObjectArray;
        } else {
            filteredList = rawObjectArray.subList(start, (start + length > rawObjectArray.size() ? rawObjectArray.size() : start + length));
        }

        DataTables<T> dataTables = new DataTables<>();
        dataTables.setRecordsTotal(totalRegisters);
        dataTables.setRecordsFiltered(search == null ? totalRegisters : rawObjectArray.size());

        for (Object[] rawObjects : filteredList) {
            dataTables.getData().add((T) this.clazz.getDeclaredConstructor(Object[].class).newInstance(new Object[]{rawObjects}));
        }
        return dataTables;
    }
}
