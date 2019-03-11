package com.oracle.auto.web.jbehave_ext;

import com.oracle.auto.commons.exceptions.snapshots.SnapshotTaken;
import com.oracle.auto.mobile.exceptions.MobileExceptionBase;
import com.oracle.auto.web.exceptions.WebDriverExceptionEx;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.reporters.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;

/**
 * <p>
 * Story reporter that outputs to a PrintStream, as HTML. It extends
 * {@link PrintStreamOutput}, providing HTML-based default output
 * patterns, which can be overridden via the {@link
 * HtmlWebOutput (PrintStream,Properties)} constructor.
 * </p>
 *
 * @author Mirko FriedenHagen
 * @author Mauro Talevi
 */
public class HtmlWebOutput extends HtmlOutput {

    public static final org.jbehave.core.reporters.Format HTML_WEB = new org.jbehave.core.reporters.Format("HTML") {
        @Override
        public StoryReporter createStoryReporter(FilePrintStreamFactory factory, StoryReporterBuilder storyReporterBuilder) {
            factory.useConfiguration(storyReporterBuilder.fileConfiguration("html"));
            return new HtmlWebOutput(factory.createPrintStream(), storyReporterBuilder.keywords()).doReportFailureTrace(
                    storyReporterBuilder.reportFailureTrace()).doCompressFailureTrace(storyReporterBuilder.compressFailureTrace());
        }
    };

    public HtmlWebOutput(PrintStream output) {
        super(output, new Properties());
    }

    public HtmlWebOutput(PrintStream output, Properties outputPatterns) {
        super(output, outputPatterns, new LocalizedKeywords());
    }

    public HtmlWebOutput(PrintStream output, Keywords keywords) {
        super(output, new Properties(), keywords);
    }

    public HtmlWebOutput(PrintStream output, Properties outputPatterns, Keywords keywords) {
        super(output, outputPatterns, keywords, true);
    }

    private int snapshot_preview_width = PropertyConfiger.instance().getEnvData("report.snapshot.in.preview.width", 800);
    final private int snapshot_preview_heigth = PropertyConfiger.instance().getEnvData("report.snapshot.in.preview.width.height", 600);

    @Override
    public void restarted(String step, Throwable cause) {
        super.overwritePattern("restarted", "<div class=\"step restarted\"><span class=\"message restarted\">{0}</span></div>\n");
        super.overwritePattern("retryWithFailureStack", "<pre class=\"step restarted\">{0}</pre>\n</div>\n");

        print(format("restarted", "{0}\n", cause.getMessage()));

        showSnapshot(cause);

        print("<div class=\"step restarted\">\n");
        print(format("retryWithFailureStack", "\n{0}\n", new StackTraceFormatter(compressFailureTrace()).stackTrace(cause.getCause())));
    }

    @Override
    public void failed(String step, Throwable storyFailure) {
        super.overwritePattern("failed", "<div class=\"step failed\">{0} <span class=\"keyword failed\">({1})</span></div>\n");
        super.failed(step, storyFailure);

        showSnapshot(storyFailure);

        print("<div class=\"failure\">\n");
        // print full stack
        super.afterScenario();
    }

    @Override
    public void afterScenario() {
        print(format("afterScenario", "\n"));
    }

    private void showSnapshot(Throwable storyFailure) {
        // show snapshots
        SnapshotTaken st = null;
        if (st == null && storyFailure.getCause() instanceof SnapshotTaken){
            st = (SnapshotTaken) (storyFailure.getCause());

            if (storyFailure.getCause() instanceof MobileExceptionBase)
                snapshot_preview_width = 350;
        }

        if (st == null && storyFailure.getCause() != null) st = new WebDriverExceptionEx(storyFailure.getCause());

        // show snapshots in html report.
        if (st != null && st.getSnapshots().size() > 0) {
            print("<div class=\"failure\" style=\"border-width: 1px; border-style: dotted; border-color: black; width: " + (snapshot_preview_width) + "px; \">");
            List<String> snapshots = st.getSnapshots();
            for (String snapshot : snapshots)
                try {
                    doPrintSnapshot(snapshot);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            print("</div>\n");
        }
    }

    private void doPrintSnapshot(String snapshot) throws IOException {
        String template = "<a href=\"%s\" target=\"_blank\"><img border=0 src=\"%s\" width=%d height=%d /></a><br>";
        File file = new File(snapshot);
        String filePath = "../" + file.getName();
        BufferedImage bimg = ImageIO.read(file);
        double width = bimg.getWidth();
        double height = bimg.getHeight();
        double degree = (width > snapshot_preview_width) ? (snapshot_preview_width * 1.0) / width : (height > snapshot_preview_heigth ? (snapshot_preview_heigth * 1.0) / height : 1);
        width *= degree;
        height *= degree;
        print(String.format(template, filePath, filePath, (int) width, (int) height));
    }
}
