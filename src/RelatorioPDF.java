import java.sql.*;
import java.io.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import java.util.Date;
import java.text.SimpleDateFormat;

public class RelatorioPDF {
    private String ip;
    private Connection con;
    public Statement stm,stm2;
    public ResultSet rs, rs2;

    public RelatorioPDF(int id) throws Exception {
        Document doc = null;
        OutputStream os = null;
                
        try {
            InputStream is = new FileInputStream("conf.inf");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            ip = br.readLine();
            br.close();
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://"+ip+"/db_mcmv", "user_mcmv", "admin");
            stm = con.createStatement();
            rs = stm.executeQuery("select * from principal where id = "+id);
            rs.next();

            String nome = rs.getString("nome");
            String sexo, zona, cadunico, blFam, bpc, risco;
            if (rs.getString("sexo").equals("0")) {
                sexo = "Masculino";
            } else {
                sexo = "Feminino";
            }

            if (rs.getString("zona").equals("0")) {
                zona = "Urbana";
            } else {
                zona = "Rural";
            }

            if (rs.getString("cadunico").equals("0")) {
                cadunico = "Sim";
            } else {
                cadunico = "Não";
            }

            if (rs.getString("bolsa_familia").equals("0")) {
                blFam = "Sim";
            } else {
                blFam = "Não";
            }

            if (rs.getString("bpc").equals("0")) {
                bpc = "Sim";
            } else {
                bpc = "Não";
            }

            if (rs.getString("risco").equals("0")) {
                risco = "Sim";
            } else {
                risco = "Não";
            }
            
            //cria o documento tamanho A4
            doc = new Document(PageSize.A4, 65, 60, 30, 20);
            os = new FileOutputStream("Relatório.pdf");
            PdfWriter.getInstance(doc, os);
            doc.open();

            Image pref = Image.getInstance("imagem\\prefeitura.png");
            pref.setAlignment(Element.PARAGRAPH);
            doc.add(pref);

            Paragraph p = new Paragraph();
            p.add(new Phrase("\t\tPREFEITURA MUNICIPAL DE TEÓFILO OTONI\n", new Font(FontFamily.HELVETICA, 17, Font.BOLD)));
            p.add(new Phrase("\t\tSECRETARIA MUNICIPAL DE ASSITÊNCIA SOCIAL E HABITAÇÃO", new Font(FontFamily.HELVETICA, 12, Font.BOLD)));
            p.setSpacingBefore(10);
            doc.add(p);

            p = new Paragraph("\t\tCADASTRO GERAL DA HABITAÇÃO", new Font(FontFamily.HELVETICA, 22, Font.NORMAL));
            p.setSpacingAfter(5);
            doc.add(p);

            Font fonte1 = new Font(FontFamily.HELVETICA, 8, Font.BOLD);
            Font fonte2 = new Font(FontFamily.HELVETICA, 8, Font.NORMAL);

            SimpleDateFormat formato = new SimpleDateFormat("d' de 'MMMM' de 'yyyy");
            try {
                PdfPTable tabela = new PdfPTable(new float[] {6, 5});
                tabela.setWidthPercentage(100);
                tabela.setHorizontalAlignment(Element.ALIGN_CENTER);
                
                p = new Paragraph();
                p.add(new Phrase("NOME: ", fonte1));
                p.add(new Phrase(nome, fonte2));
                PdfPCell celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(1);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("SEXO: ", fonte1));
                p.add(new Phrase(sexo, fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("DATA DE NASCIMENTO: ", fonte1));
                p.add(new Phrase(formato.format(rs.getDate("dt_nasc")), fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("ESTADO CIVIL: ", fonte1));
                p.add(new Phrase(rs.getString("est_civil"), fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("E-MAIL: ", fonte1));
                p.add(new Phrase(rs.getString("email"), fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("TELEFONE: ", fonte1));
                p.add(new Phrase(rs.getString("telefone"), fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("CPF: ", fonte1));
                p.add(new Phrase(rs.getString("cpf"), fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("RG: ", fonte1));
                p.add(new Phrase(rs.getString("rg"), fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("ENDEREÇO: ", fonte1));
                p.add(new Phrase(rs.getString("endereco"), fonte2));
                celula = new PdfPCell(p);
                celula.setColspan(2);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("BAIRRO: ", fonte1));
                p.add(new Phrase(rs.getString("bairro"), fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("ZONA: ", fonte1));
                p.add(new Phrase(zona, fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("NATURALIDADE: ", fonte1));
                p.add(new Phrase(rs.getString("naturalidade"), fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("TEMPO DE MORADIA EM TEÓFILO OTONI: ", fonte1));
                p.add(new Phrase(rs.getString("tempo")+" anos", fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);
                
                p = new Paragraph();
                p.add(new Phrase("OCUPAÇÃO: ", fonte1));
                p.add(new Phrase(rs.getString("ocupacao"), fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("REMUNERAÇÃO: ", fonte1));
                p.add(new Phrase("R$ "+Math.round(rs.getDouble("remuneracao"))+",00", fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("OUTRAS RENDAS: ", fonte1));
                p.add(new Phrase("R$ "+Math.round(rs.getDouble("outras_rendas"))+",00", fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("NÍVEL DE ESCOLARIDADE: ", fonte1));
                p.add(new Phrase(rs.getString("escolaridade"), fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("CADÚNICO: ", fonte1));
                p.add(new Phrase(cadunico, fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("NIS: ", fonte1));
                p.add(new Phrase(rs.getString("nis"), fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("BOLSA FAMÍLIA: ", fonte1));
                p.add(new Phrase(blFam, fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("BPC: ", fonte1));
                p.add(new Phrase(bpc, fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("SITUAÇÃO DO IMÓVEL: ", fonte1));
                p.add(new Phrase(rs.getString("imovel"), fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("QUANTIDADE DE CÔMODOS: ", fonte1));
                p.add(new Phrase(rs.getString("comodos"), fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("ALUGUEL: ", fonte1));
                p.add(new Phrase("R$ "+Math.round(rs.getDouble("aluguel"))+",00", fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("ÁREA DE RISCO: ", fonte1));
                p.add(new Phrase(risco, fonte2));
                celula = new PdfPCell(p);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);
                
                p = new Paragraph();
                p.add(new Phrase("TIPO DE DEFICIÊNCIA ENCONTRADO EM MEMBRO DA FAMÍLIA: ", fonte1));
                p.add(new Phrase(rs.getString("deficiencia"), fonte2));
                celula = new PdfPCell(p);
                celula.setColspan(2);
                celula.setExtraParagraphSpace(5);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("OBSERVAÇÃO: ", fonte1));
                p.add(new Phrase(rs.getString("observ"), fonte2));
                celula = new PdfPCell(p);
                celula.setColspan(2);
                celula.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                celula.setBorder(0);
                tabela.addCell(celula);

                tabela.setSpacingAfter(10);
                doc.add(tabela);
            } catch (Exception e) {
            }

            stm2 = con.createStatement();
            rs2 = stm2.executeQuery("select * from dependentes where fk_id = "+id);
            PdfPTable tabela = new PdfPTable(new float[] {7, 1, 3, 3, 3, 2, 2});
            tabela.setWidthPercentage(100);
            tabela.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celula = new PdfPCell(new Paragraph("COMPOSIÇÃO FAMILIAR", new Font(FontFamily.HELVETICA, 12, Font.BOLD)));
            celula.setColspan(7);
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabela.addCell(celula);
            celula = new PdfPCell(new Paragraph("NOME", fonte1));
            celula.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabela.addCell(celula);
            celula = new PdfPCell(new Paragraph("IDA-\nDE", fonte1));
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabela.addCell(celula);
            celula = new PdfPCell(new Paragraph("PARENTESCO", fonte1));
            celula.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabela.addCell(celula);
            celula = new PdfPCell(new Paragraph("DOCUMENTO", fonte1));
            celula.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabela.addCell(celula);
            celula = new PdfPCell(new Paragraph("OCUPAÇÃO", fonte1));
            celula.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabela.addCell(celula);
            celula = new PdfPCell(new Paragraph("REMUNE-\nRAÇÃO", fonte1));
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabela.addCell(celula);
            celula = new PdfPCell(new Paragraph("OUTRAS\nRENDAS", fonte1));
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabela.addCell(celula);
            for (int i = 0; i < 10; i++) {
                if (rs2.next()) {
                    celula = new PdfPCell(new Paragraph(rs2.getString("nome"), fonte2));
                    tabela.addCell(celula);
                    celula = new PdfPCell(new Paragraph(rs2.getString("idade"), fonte2));
                    celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabela.addCell(celula);
                    celula = new PdfPCell(new Paragraph(rs2.getString("parentesco"), fonte2));
                    tabela.addCell(celula);
                    celula = new PdfPCell(new Paragraph(rs2.getString("documento"), fonte2));
                    celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabela.addCell(celula);
                    celula = new PdfPCell(new Paragraph(rs2.getString("ocupacao"), fonte2));
                    tabela.addCell(celula);
                    celula = new PdfPCell(new Paragraph("R$ "+rs2.getString("remuneracao")+",00", fonte2));
                    celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabela.addCell(celula);
                    celula = new PdfPCell(new Paragraph("R$ "+rs2.getString("outras_rendas")+",00", fonte2));
                    celula.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabela.addCell(celula);
                } else {
                    celula = new PdfPCell(new Paragraph("*************************************************", fonte2));
                    tabela.addCell(celula);
                    celula = new PdfPCell(new Paragraph("*****", fonte2));
                    tabela.addCell(celula);
                    celula = new PdfPCell(new Paragraph("********************", fonte2));
                    tabela.addCell(celula);
                    celula = new PdfPCell(new Paragraph("********************", fonte2));
                    tabela.addCell(celula);
                    celula = new PdfPCell(new Paragraph("********************", fonte2));
                    tabela.addCell(celula);
                    celula = new PdfPCell(new Paragraph("*************", fonte2));
                    tabela.addCell(celula);
                    celula = new PdfPCell(new Paragraph("*************", fonte2));
                    tabela.addCell(celula);
                }
            }
            tabela.setSpacingAfter(5);
            doc.add(tabela);
            
            p = new Paragraph("Declaro, sob as penas da lei (Art. 299 do Código Penal), que as declarações contidas neste formulário correspondem à verdade e comprometo-me a procurar a Secretaria Municipal de Assistência Social e Habitação para atualizá-las sempre que houver mudanças em relação às informações prestadas por mim nesta entrevista ou, no máximo, em até dois anos da data desta entrevista.", fonte2);
            p.add(new Phrase(" Teófilo Otoni, "+formato.format(new Date())+".", fonte1));
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            p.setSpacingAfter(10);
            doc.add(p);

            p = new Paragraph( "___________________________________________\n"
                    +"Assinatura do Responsável pela Unidade Familiar", fonte1);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(5);
            doc.add(p);

            p = new Paragraph( "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------", fonte1);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(5);
            doc.add(p);

            pref = Image.getInstance("imagem\\prefeitura.png");
            pref.setAlignment(Element.MULTI_COLUMN_TEXT);
            doc.add(pref);

            p = new Paragraph();
            p.add(new Phrase("PREFEITURA MUNICIPAL DE TEÓFILO OTONI\n", new Font(FontFamily.HELVETICA, 12, Font.BOLD)));
            p.add(new Phrase("SECRETARIA MUNICIPAL DE ASSITÊNCIA SOCIAL E HABITAÇÃO\n", new Font(FontFamily.HELVETICA, 8, Font.BOLD)));
            p.add(new Phrase("CADASTRO GERAL DA HABITAÇÃO", new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
            p.setAlignment(Element.ALIGN_CENTER);
            doc.add(p);

            try {
                tabela = new PdfPTable(new float[] {4, 2});
                tabela.setWidthPercentage(90);
                tabela.setHorizontalAlignment(Element.ALIGN_RIGHT);

                p = new Paragraph();
                p.add(new Phrase("NOME: ", fonte1));
                p.add(new Phrase(nome, fonte2));
                celula = new PdfPCell(p);
                celula.setBorder(0);
                tabela.addCell(celula);

                p = new Paragraph();
                p.add(new Phrase("CPF: ", fonte1));
                p.add(new Phrase(rs.getString("cpf"), fonte2));
                celula = new PdfPCell(p);
                celula.setBorder(0);
                tabela.addCell(celula);

                doc.add(tabela);
            } catch (Exception e) {
            }

            p = new Paragraph("CADASTRO PENDENTE: [   ] SIM  [   ] NÃO", fonte2);
            p.setAlignment(Element.ALIGN_RIGHT);
            doc.add(p);

            p = new Paragraph("Teófilo Otoni, "+formato.format(new Date())+".", fonte1);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(10);
            doc.add(p);

            p = new Paragraph( "___________________________________________\n"
                    +"Entrevistador", fonte1);
            p.setAlignment(Element.ALIGN_CENTER);
            doc.add(p);
        } finally {
            doc.close();
            os.close();
            Runtime.getRuntime().exec("cmd.exe /C Relatório.pdf");
        }
    }
}