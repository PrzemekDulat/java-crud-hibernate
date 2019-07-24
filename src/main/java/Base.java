import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FilenameUtils;
@SuppressWarnings("Duplicates")
public class Base {
    private JTabbedPane TabbedPane;
    private JPanel panel1;
    private JPanel Zawodnicy;
    private JPanel Druzyny;
    private JPanel Mecze;
    private JPanel Terminy;
    private JPanel Wyniki;
    private JTextField imieTextField;
    private JTextField nazwiskoTextField;
    private JTextField nickTextField;
    private JComboBox selectDruzyneComboBox;
    private JButton AddZawodnik;
    private JTable table1;
    private JTable tmpTable;
    private JButton DeleteButton;
    private JButton UpdateButton;
    private TableRowSorter<TableModel> rowSorter;
    private JTextField wyszukajTextField;
    private JButton exportButton;
    private JComboBox chooseWynik;
    private JTable table2;
    private JComboBox chooseTermin;
    private JComboBox chooseDruzyne2;
    private JComboBox chooseDruzyne1;
    private JButton createMeczButton;
    private JButton updateMeczButton;
    private JButton deleteMeczButton;
    private JPanel PanelDoTabeli;
    private JPanel PanelDoTabeli2;
    private JLabel JLabelFotka;
    private JButton ChooseZdjecie;
    private JLabel ChoosedImageLabel;
    private DefaultTableModel dtmJtable1;
    private DefaultTableModel dtmJtable2;
    File ZdjecieZawodnika;
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private JComponent createAlternating(DefaultTableModel model)
    {
        table1 = new JTable( model )

        {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row))
                    c.setBackground(row % 2 == 0 ? getBackground() : new Color(237,237,237));

                return c;
            }
        };

        table1.setShowGrid(false);
        table1.setIntercellSpacing(new Dimension(0, 0));
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        //table1.setDefaultRenderer(String.class, centerRenderer);
        table1.setDefaultRenderer(Integer.class, centerRenderer);
        table1.setPreferredScrollableViewportSize(table1.getPreferredSize());
        table1.changeSelection(0, 0, false, false);
        return new JScrollPane( table1 );
    }

    private JComponent createAlternating2(DefaultTableModel model)
    {
        table2 = new JTable( model )

        {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row))
                    c.setBackground(row % 2 == 0 ? getBackground() : new Color(237,237,237));

                return c;
            }
        };

        table2.setShowGrid(false);
        table2.setIntercellSpacing(new Dimension(0, 0));
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        //table1.setDefaultRenderer(String.class, centerRenderer);
        table2.setDefaultRenderer(Integer.class, centerRenderer);
        table2.setPreferredScrollableViewportSize(table1.getPreferredSize());
        table2.changeSelection(0, 0, false, false);
        return new JScrollPane( table2 );
    }

    public static void CreateAndShowUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame("Turniej");

        frame.setContentPane(new Base().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(863, 477);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        CreateAndShowUI();

    }

    public Base() {

        PanelDoTabeli.add("Kolory",createAlternating(dtmJtable1));
        PanelDoTabeli2.add("Kolory",createAlternating2(dtmJtable2));
        JLabelFotka.setIcon(new ImageIcon("C:\\ZdjeciaHib\\BrakZdjecia.jpg"));
        ChoosedImageLabel.setText("Brak Zdjecia!");

        LoadZawodnikToTable();
        LoadComboBox();
        TestTableSortFilter();
        LoadDruzyna1ComboBox();
        LoadDruzyna2ComboBox();
        LoadTerminyComboBox();
        LoadWynikiComboBox();
        LoadMeczeToTable();

        AddZawodnik.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateButtonFun();
                LoadZawodnikToTable();
                TestTableSortFilter();
            }
        });

        DeleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeleteButtonFun();
                LoadZawodnikToTable();
                TestTableSortFilter();
            }
        });

        UpdateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateButtonFun();
                LoadZawodnikToTable();
                TestTableSortFilter();
            }
        });


        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    System.out.println("Selection Changed");
                    if(table1.getSelectedRow() != -1){
                    int row = table1.getSelectedRow();
                    int imieCol = 1;
                    int nickCol = 2;
                    int nazwiskoCol = 3;
                    int druzynaCol = 4;
                        String imie = (String)table1.getValueAt(row, imieCol);
                        String nick = (String)table1.getValueAt(row, nickCol);
                        String nazwisko = (String)table1.getValueAt(row, nazwiskoCol);
                        String druzyna = (String)table1.getValueAt(row, druzynaCol);
                        imieTextField.setText(imie);
                        nickTextField.setText(nick);
                        nazwiskoTextField.setText(nazwisko);
                        if(druzyna.equals("Brak druzyny")){
                            selectDruzyneComboBox.setSelectedIndex(0);
                        }
                        selectDruzyneComboBox.setSelectedItem(table1.getValueAt(row, druzynaCol));
                    } else {return;}



                    int row = table1.getSelectedRow();
                    int idZawodnika = (Integer)table1.getValueAt(row, 0);

                    Zawodnik zawodnik = (Zawodnik) entityManager.createQuery("SELECT z FROM Zawodnik z where z.idZawodnik = :iDzawodnika").setParameter("iDzawodnika", idZawodnika).getSingleResult();


                    byte[] fotka =  zawodnik.getFotka();

                    if (fotka != null){
                        try {
                            BufferedImage img = ImageIO.read(new ByteArrayInputStream(fotka));
                            Image Scaleimg = img.getScaledInstance(JLabelFotka.getWidth(), JLabelFotka.getHeight(),Image.SCALE_SMOOTH);
                            JLabelFotka.setIcon(new ImageIcon(Scaleimg));
                            //JLabelFotka.setIcon(new ImageIcon("C:\\ZdjeciaHib\\Pashabiceps.png"));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("BRAK FOTKI");
                        JLabelFotka.setIcon(new ImageIcon("C:\\ZdjeciaHib\\BrakZdjecia.jpg"));
                    }



                }
            }
        });


        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ExportToPdf();
            }
        });
        createMeczButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateMeczButton();
                LoadMeczeToTable();
            }
        });
        updateMeczButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateMeczButton();
                LoadMeczeToTable();
            }
        });
        deleteMeczButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeleteMeczButton();
                LoadMeczeToTable();
            }
        });

        ChooseZdjecie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChooseZdjecieFun();
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////Zawodnicy TAB////////////////

    public void ChooseZdjecieFun(){

        JFileChooser chooser = new JFileChooser();

        chooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));

        chooser.setCurrentDirectory(new File("C:\\ZdjeciaHib"));

        int response = chooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            ZdjecieZawodnika = chooser.getSelectedFile();

            ChoosedImageLabel.setText(ZdjecieZawodnika.getName());
        }
    }

    public List<Druzyna> DruzynyList(){
        List<Druzyna> druzyny = entityManager.createQuery("SELECT d FROM Druzyna d").getResultList();
        return druzyny;
    }

    public void LoadComboBox(){

        List<Druzyna> druzyny = DruzynyList();

        selectDruzyneComboBox.addItem("Brak Druzyny");

        for(int i = 0; i<druzyny.size(); i++){

            selectDruzyneComboBox.addItem(druzyny.get(i).getNazwa());
        }
    }

    public void LoadZawodnikToTable(){

        List<Zawodnik> zawodnikList = entityManager.createQuery("SELECT z FROM Zawodnik z").getResultList();
        //Druzyna druzyna = entityManager.createQuery("SELECT d FROM Druzyna d where d.zawodnikList").getSingleResult();
        Vector<String> tableHeaders = new Vector<String>();
        Vector tableData = new Vector();
        tableHeaders.add("Id");
        tableHeaders.add("Imie");
        tableHeaders.add("Nick");
        tableHeaders.add("Nazwisko");
        tableHeaders.add("Druzyna");

        for(Object o : zawodnikList) {
            Zawodnik zawodnik = (Zawodnik) o;
            Vector<Object> oneRow = new Vector<Object>();
            oneRow.add(zawodnik.getIdZawodnik());
            oneRow.add(zawodnik.getImie());
            oneRow.add(zawodnik.getNick());
            oneRow.add(zawodnik.getNazwisko());
            if (zawodnik.getDruzyna() == null){
                oneRow.add("Brak druzyny");
            }else{
                oneRow.add(zawodnik.getDruzyna().getNazwa());
            }
            tableData.add(oneRow);
        }

        dtmJtable1 = new DefaultTableModel(tableData, tableHeaders){
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };



        table1.setModel(dtmJtable1);

        table1.setAutoCreateRowSorter(true);


    }

    public void CreateButtonFun(){

        Zawodnik zawodnik = new Zawodnik();

        if(selectDruzyneComboBox.getSelectedItem() == "Brak Druzyny"){          //WYBIERANIE DRUZYNY DLA ZAWODNIKA
            zawodnik.setDruzyna(null);
        } else{
            Druzyna druzyna = (Druzyna) entityManager.
                    createQuery("SELECT d FROM Druzyna d where d.nazwa = :nazwaCB").
                    setParameter("nazwaCB", selectDruzyneComboBox.getSelectedItem()).
                    getSingleResult();
            zawodnik.setDruzyna(druzyna);
        }

        zawodnik.setImie(imieTextField.getText());              //NADAWANIE IMIONA NICKU I NAZWISKA ZAWODNIKOWI
        zawodnik.setNick(nickTextField.getText());
        zawodnik.setNazwisko(nazwiskoTextField.getText());

        if(ZdjecieZawodnika != null) {
        byte[] imageData = new byte[(int) ZdjecieZawodnika.length()];


            try {
                FileInputStream fileInputStream = new FileInputStream(ZdjecieZawodnika);    //WYBIERANIE ZDJECIA ZAWODNIKA
                fileInputStream.read(imageData);
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            zawodnik.setFotka(imageData);
            imageData = null;
        }

        entityManager.getTransaction().begin();
        entityManager.persist(zawodnik);            //DODANIE ZAWODNIKA DO BAZY DANYCH
        entityManager.getTransaction().commit();

        ZdjecieZawodnika = null;                    //WYCZYSZCZENIE TEXTFIELDOW I LABELI
        ChoosedImageLabel.setText("Brak Zdjecia!");
        imieTextField.setText("");
        nickTextField.setText("");
        nazwiskoTextField.setText("");
        selectDruzyneComboBox.setSelectedIndex(0);

    }

    public void DeleteButtonFun(){


        int[] rows = table1.getSelectedRows();

        for(int i=0; i < rows.length; i++){
            int idZawodnika = (Integer)table1.getValueAt(rows[i], 0);

            Zawodnik zawodnik = (Zawodnik) entityManager.
                    createQuery("SELECT z FROM Zawodnik z where z.idZawodnik = :iDzawodnika").setParameter("iDzawodnika", idZawodnika).getSingleResult();

            entityManager.getTransaction().begin();
            entityManager.remove(zawodnik);
            entityManager.getTransaction().commit();
        }

    }

    public void UpdateButtonFun(){

        int row = table1.getSelectedRow();
        int idZawodnika = (Integer)table1.getValueAt(row, 0);

        Zawodnik zawodnik = (Zawodnik) entityManager.
                createQuery("SELECT z FROM Zawodnik z where z.idZawodnik = :iDzawodnika").
                setParameter("iDzawodnika", idZawodnika).getSingleResult();


        entityManager.getTransaction().begin();
        if(selectDruzyneComboBox.getSelectedItem() == "Brak Druzyny"){
            zawodnik.setDruzyna(null);
        } else{
            Druzyna druzyna = (Druzyna) entityManager.
                    createQuery("SELECT d FROM Druzyna d where d.nazwa = :nazwaCB").
                    setParameter("nazwaCB", selectDruzyneComboBox.getSelectedItem()).
                    getSingleResult();
            zawodnik.setDruzyna(druzyna);
        }

        if(ZdjecieZawodnika != null) {
        byte[] imageData = new byte[(int) ZdjecieZawodnika.length()];

            try {
                FileInputStream fileInputStream = new FileInputStream(ZdjecieZawodnika);
                fileInputStream.read(imageData);
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            zawodnik.setFotka(imageData);
            imageData = null;
        }
        zawodnik.setImie(imieTextField.getText());                             //AKTUALIZOWANIE ZAWODNIKA
        zawodnik.setNick(nickTextField.getText());
        zawodnik.setNazwisko(nazwiskoTextField.getText());
        entityManager.getTransaction().commit();

        ZdjecieZawodnika = null;

        ChoosedImageLabel.setText("Brak Zdjecia!");
    }

    public void TestTableSortFilter() {

        rowSorter = new TableRowSorter<TableModel>(dtmJtable1);
        table1.setRowSorter(rowSorter);
        wyszukajTextField.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = wyszukajTextField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = wyszukajTextField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });


    }

    public void ExportToPdf(){

        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setCurrentDirectory(new File("C:/Users/"+System.getProperty("user.name")+"/Desktop"));



        int response = chooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("pdf")) {
                    // filename is OK as-is
                } else {
                    file = new File(file.toString() + ".pdf");
                    file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName())+".pdf"); // ALTERNATIVELY: remove the extension (if any) and replace it with ".xml"
                }
                Document doc = new Document();
                PdfWriter.getInstance(doc, new FileOutputStream(chooser.getSelectedFile()+".pdf"));
                doc.open();
                PdfPTable pdfTable = new PdfPTable(table1.getColumnCount());
                //adding table headers
                for (int i = 0; i < table1.getColumnCount(); i++) {
                    pdfTable.addCell(table1.getColumnName(i));
                }
                //extracting data from the JTable and inserting it to PdfPTable
                for (int rows = 0; rows < table1.getRowCount(); rows++) {
                    for (int cols = 0; cols < table1.getColumnCount(); cols++) {
                        pdfTable.addCell(table1.getModel().getValueAt(table1.convertRowIndexToModel(rows), cols).toString());
                    }
                }
                doc.add(pdfTable);
                doc.close();
                System.out.println("File saved");
            } catch (DocumentException ex) {
                Logger.getLogger(Base.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Base.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////Mecze TAB/////////////////


    public void LoadDruzyna1ComboBox(){

        List<Druzyna> druzyny = DruzynyList();

        for(int i = 0; i<druzyny.size(); i++){

            chooseDruzyne1.addItem(druzyny.get(i).getNazwa());
        }

    }

    public void LoadDruzyna2ComboBox(){

        List<Druzyna> druzyny = DruzynyList();

        for(int i = 0; i<druzyny.size(); i++){

            chooseDruzyne2.addItem(druzyny.get(i).getNazwa());
        }

    }

    public void LoadTerminyComboBox(){

        List<Termin> terminy = entityManager.createQuery("SELECT t FROM Termin t").getResultList();

        for(int i = 0; i<terminy.size(); i++){

            chooseTermin.addItem(terminy.get(i).getData());
        }

    }

    public void LoadMeczeToTable(){

        List<Mecz> meczList = entityManager.createQuery("SELECT m FROM Mecz m").getResultList();
        //Druzyna druzyna = entityManager.createQuery("SELECT d FROM Druzyna d where d.zawodnikList").getSingleResult();
        Vector<String> tableHeaders = new Vector<String>();
        Vector tableData = new Vector();
        tableHeaders.add("Id");
        tableHeaders.add("Druzyna 1");
        tableHeaders.add("Wynik");
        tableHeaders.add("Druzyna 2");
        tableHeaders.add("Termin");

        for(Object o : meczList) {
            Mecz mecz = (Mecz) o;
            Vector<Object> oneRow = new Vector<Object>();
            oneRow.add(mecz.getIdMecz());
            oneRow.add(mecz.getIdDruzyna1().getNazwa());
            oneRow.add(mecz.getIdWyniku().getWygraneRundyDruzyny1() +":" +mecz.getIdWyniku().getWygraneRundyDruzyny2());
            oneRow.add(mecz.getIdDruzyna2().getNazwa());
            oneRow.add(mecz.getIdTerminu().getData());

            tableData.add(oneRow);
        }

        dtmJtable2 = new DefaultTableModel(tableData, tableHeaders){
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };



        table2.setModel(dtmJtable2);

        table2.setAutoCreateRowSorter(true);

        table2.setAutoCreateRowSorter(true);

    }

    public void LoadWynikiComboBox(){

        List<Wynik> wyniki = entityManager.createQuery("SELECT w FROM Wynik w").getResultList();

        for(int i = 0; i<wyniki.size(); i++){

            chooseWynik.addItem(wyniki.get(i).getWygraneRundyDruzyny1()+":"+wyniki.get(i).getWygraneRundyDruzyny2());
        }
    }

    public void CreateMeczButton(){

        Druzyna druzyna1 = (Druzyna) entityManager.createQuery("SELECT d FROM Druzyna d where d.nazwa = :nazwaCB").setParameter("nazwaCB", chooseDruzyne1.getSelectedItem()).getSingleResult();
        Druzyna druzyna2 = (Druzyna) entityManager.createQuery("SELECT d FROM Druzyna d where d.nazwa = :nazwaCB").setParameter("nazwaCB", chooseDruzyne2.getSelectedItem()).getSingleResult();
        Termin termin = (Termin) entityManager.createQuery("SELECT t FROM Termin t where t.data = :dataCB").setParameter("dataCB", chooseTermin.getSelectedItem()).getSingleResult();
        Wynik wynik = (Wynik) entityManager.createQuery("SELECT w FROM Wynik w where w.idWynik = :idWynik").setParameter("idWynik", chooseWynik.getSelectedIndex()+1).getSingleResult();

        Mecz mecz = new Mecz();

        mecz.setIdTerminu(termin);
        mecz.setIdWyniku(wynik);
        mecz.setIdDruzyna1(druzyna1);
        mecz.setIdDruzyna2(druzyna2);

        entityManager.getTransaction().begin();
        entityManager.persist(mecz);
        entityManager.getTransaction().commit();


    }

    public void UpdateMeczButton(){

        int row = table2.getSelectedRow();
        int idMeczu = (Integer)table2.getValueAt(row, 0);

        Mecz mecz = (Mecz) entityManager.createQuery("SELECT m FROM Mecz m where m.idMecz = :iDmeczu").setParameter("iDmeczu", idMeczu).getSingleResult();
        Druzyna druzyna1 = (Druzyna) entityManager.createQuery("SELECT d FROM Druzyna d where d.nazwa = :nazwaCB").setParameter("nazwaCB", chooseDruzyne1.getSelectedItem()).getSingleResult();
        Druzyna druzyna2 = (Druzyna) entityManager.createQuery("SELECT d FROM Druzyna d where d.nazwa = :nazwaCB").setParameter("nazwaCB", chooseDruzyne2.getSelectedItem()).getSingleResult();
        Termin termin = (Termin) entityManager.createQuery("SELECT t FROM Termin t where t.data = :dataCB").setParameter("dataCB", chooseTermin.getSelectedItem()).getSingleResult();
        Wynik wynik = (Wynik) entityManager.createQuery("SELECT w FROM Wynik w where w.idWynik = :idWynik").setParameter("idWynik", chooseWynik.getSelectedIndex()+1).getSingleResult();


        entityManager.getTransaction().begin();
        mecz.setIdTerminu(termin);
        mecz.setIdWyniku(wynik);
        mecz.setIdDruzyna1(druzyna1);
        mecz.setIdDruzyna2(druzyna2);
        entityManager.getTransaction().commit();

    }

    public void  DeleteMeczButton(){
        int row = table2.getSelectedRow();
        int idMeczu = (Integer)table2.getValueAt(row, 0);

        Mecz mecz = (Mecz) entityManager.createQuery("SELECT m FROM Mecz m where m.idMecz = :iDmeczu").setParameter("iDmeczu", idMeczu).getSingleResult();

        entityManager.getTransaction().begin();
        entityManager.remove(mecz);
        entityManager.getTransaction().commit();
    }

}


