package com.comic.backend.utils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Created by datha on 4/6/2018.
 */
public class BaseUtils {
    private static Logger logger = Logger.getLogger(BaseUtils.class);

    public static DataTablePaginationRequest getDatatableRequestInfo(MultiValueMap<String, String> params) {
        DataTablePaginationRequest dtpr = new DataTablePaginationRequest();
        try {
            int draw = Integer.parseInt(params.get("draw").get(0));
            int start = Integer.parseInt(params.get("start").get(0));
            int length = Integer.parseInt(params.get("length").get(0));
            String searchValue = params.get("search[value]").get(0);
            String type = params.get("type").get(0);
            List<String> typeData = "FRIEND".equalsIgnoreCase(type) ? params.get("type_data[]"): params.get("type_data");
            dtpr.setDraw(draw);
            dtpr.setStart(start);
            dtpr.setLength(length);
            dtpr.setSearchValue(searchValue);
            dtpr.setType(type);
            dtpr.setTypeData(typeData);
        } catch (Exception e) {
            except(logger, e);
        }

        int i = 0;
        Map<Integer, DataTableColumn> columns = new HashMap<Integer, DataTableColumn>();
        while (params.containsKey(dataTableRequestGetColumnKey(i, "data"))) {
            int index = i;
            String data = params.get(dataTableRequestGetColumnKey(i, "data")).get(0);
            String name = params.get(dataTableRequestGetColumnKey(i, "name")).get(0);
            String searchValue = params.get(String.format("columns[%d][search][value]", i)).get(0);
            DataTableColumn dataTableColumn = new DataTableColumn(index, data, name, searchValue);
            columns.put(i, dataTableColumn);
            i++;
        }
        dtpr.setColumns(columns);

        String orderColumnIndex = params.get("order[0][column]").get(0);

        String orderDirection = params.get("order[0][dir]").get(0);

        dtpr.setOrderColumnIndex(Integer.parseInt(orderColumnIndex));
        dtpr.setOrderDirection(orderDirection);

        return dtpr;
    }

    public static String dataTableRequestGetColumnKey(int index, String propName) {
        return String.format("columns[%d][%s]", index, propName);
    }

    public static String dataTableRequestGetFilterConditionKey(int index, String propName) {
        return String.format("filterCondition[%d][%s]", index, propName);
    }

    public static void except(Logger _logger, Exception ex) {
        if (_logger == null) {
            _logger.info("logger is null, use BaseUtils logger for logging!");
        } else {
            _logger.error("Has exception. ", ex);
        }
    }
}
