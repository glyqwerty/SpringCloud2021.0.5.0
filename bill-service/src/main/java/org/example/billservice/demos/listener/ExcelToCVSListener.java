package org.example.billservice.demos.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.example.domain.InBillDetailInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExcelToCVSListener extends AnalysisEventListener<InBillDetailInfo> {

    private BufferedWriter writer;
    public ExcelToCVSListener(File csvFile) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(csvFile));
    }

    @Override
    public void invoke(InBillDetailInfo data, AnalysisContext context) {

        try { String line =
                data.getBillCode() + "," +
                        data.getMaterialCode() + "," +
                        data.getMaterialName() + "," +
                        data.getQty();
            writer.write(line);
            writer.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        try {
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
