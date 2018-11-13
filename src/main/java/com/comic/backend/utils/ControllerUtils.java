package com.comic.backend.utils;


import com.comic.backend.book.BookController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.MultiValueMap;
import java.util.List;


public class ControllerUtils<T> {

    private Logger logger = LogManager.getLogger(ControllerUtils.class);

    public DataTablePaginationResponse getPaginationData(JpaSpecificationExecutor baseRepository, MultiValueMap<String, String> params) {
        try {
            logger.info("json: " + params);
            DataTablePaginationRequest dtpr = BaseUtils.getDatatableRequestInfo(params);

            long draw = dtpr.getDraw();
            long start = dtpr.getStart();
            long length = dtpr.getLength();


            long page = start / length;


            Specifications specifications = dtpr.getSpecifications();
            List<T> data;
            long totalRows = baseRepository.count(specifications);
            data = baseRepository.findAll(specifications, dtpr.getPageRequest()).getContent();
            long recordsFiltered = baseRepository.count(specifications);

            logger.info(String.format("draw: %d, start: %d, length: %d, total row: %d, page: %d", draw, start, length, totalRows, page));


            return new DataTablePaginationResponse(draw, totalRows, recordsFiltered, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
