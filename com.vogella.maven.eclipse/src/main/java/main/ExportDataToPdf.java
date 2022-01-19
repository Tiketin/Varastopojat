package main;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.graphics.*;
import com.spire.pdf.tables.*;
import com.spire.pdf.tables.table.*;
import com.spire.pdf.tables.table.common.JdbcAdapter;
import java.awt.geom.*;
import java.awt.*;
import java.sql.*;

public class ExportDataToPdf {

	public static void main(String[] args) {

		// create a PDF document
		PdfDocument doc = new PdfDocument();

		// set page margins
		doc.getPageSettings().setMargins(30f, 30f, 30f, 30f);

		// add a page
		PdfPageBase page = doc.getPages().add();

		// initialize y coordinate
		float y = 0;

		// create a brush
		PdfBrush brush = PdfBrushes.getBlack();

		// create four types of fonts
		PdfTrueTypeFont titleFont = new PdfTrueTypeFont(new Font("Arial", Font.BOLD, 16));
		PdfTrueTypeFont tableFont = new PdfTrueTypeFont(new Font("Arial", 0, 10));
		PdfTrueTypeFont headerFont = new PdfTrueTypeFont(new Font("Arial", Font.BOLD, 11));
		PdfTrueTypeFont textFont = new PdfTrueTypeFont(new Font("Arial", 0, 12));

		// draw title on the center of the page
		PdfStringFormat format = new PdfStringFormat(PdfTextAlignment.Center);
		page.getCanvas().drawString("Keräilylista", titleFont, brush, page.getCanvas().getClientSize().getWidth() / 2,
				y, format);

		// calculate y coordinate
		y = y + (float) titleFont.measureString("Keräilylista", format).getHeight();
		y = y + 5;

		// create a PdfTable instance
		PdfTable table = new PdfTable();

		// set the default cell style and row style
		table.getStyle().setCellPadding(2);
		table.getStyle().setBorderPen(new PdfPen(brush, 0.75f));
		table.getStyle().getDefaultStyle().setBackgroundBrush(PdfBrushes.getWhite());
		table.getStyle().getDefaultStyle().setFont(tableFont);
		table.getStyle().getDefaultStyle().setStringFormat(new PdfStringFormat(PdfTextAlignment.Center));
		table.getStyle().getAlternateStyle().setBackgroundBrush(PdfBrushes.getLightGray());
		table.getStyle().getAlternateStyle().setFont(tableFont);
		table.getStyle().getAlternateStyle().setStringFormat(new PdfStringFormat(PdfTextAlignment.Center));

		// set the header style
		table.getStyle().setHeaderSource(PdfHeaderSource.Column_Captions);
		table.getStyle().getHeaderStyle().setBackgroundBrush(PdfBrushes.getPurple());
		table.getStyle().getHeaderStyle().setFont(headerFont);
		table.getStyle().getHeaderStyle().setTextBrush(PdfBrushes.getWhite());
		table.getStyle().getHeaderStyle().setStringFormat(new PdfStringFormat(PdfTextAlignment.Center));

		// show header at every page
		table.getStyle().setShowHeader(true);

		// connect to database
		//String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="+ "C:\\Users\\Administrator\\Documents\\data.mdb";
		DataTable dataTable = new DataTable();

		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		String DB_URL = "jdbc:mysql://localhost:3306/varastotest";
		final String USER = "olso";
		final String PASS = "olso";
		try {
			Class.forName("com.mysql.jdbc.Driver");  //sun.jdbc.odbc.JdbcOdbcDriver"
			try {
				Connection conn = DriverManager.getConnection(DB_URL,USER,PASS); //url
				Statement sta = conn.createStatement();
				ResultSet resultSet = sta.executeQuery("select * from products ");
				JdbcAdapter jdbcAdapter = new JdbcAdapter();
				// export data from database to datatable
				jdbcAdapter.fillDataTable(dataTable, resultSet);
				table.setDataSourceType(PdfTableDataSourceType.Table_Direct);
				// fill the table with datatable
				table.setDataSource(dataTable);

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// paginate table
		PdfTableLayoutFormat tableLayout = new PdfTableLayoutFormat();
		tableLayout.setLayout(PdfLayoutType.Paginate);

		// draw table at the specified x, y coordinates
		PdfLayoutResult result = table.draw(page, new Point2D.Float(0, y), tableLayout);

		// calculate y coordinate
		y = (float) result.getBounds().getHeight() + 5;

		// draw text under the table
		result.getPage().getCanvas().drawString(
				String.format("* %1$s ID.",table.getRows().getCount()), textFont, brush, 5, y);//"* %1$s id."

		// save pdf file.
		doc.saveToFile("Kerailylista.pdf");
		System.out.println("tulostettu" );
		System.exit(-1);
	}

}