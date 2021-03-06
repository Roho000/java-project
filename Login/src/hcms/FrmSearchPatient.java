/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hcms;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import java.time.Year;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.UIManager;

/**
 *
 * @author Harry
 */
public class FrmSearchPatient extends javax.swing.JFrame implements FocusListener, KeyListener {

    /**
     * Creates new form FrmSearchPatient
     */
    Connection con;
    Statement st;
    ResultSet rs;
    String sqlDiseaseOnly;
    String sqlDiseaseAndGender;
    String sqlDiseaseAndAge;
    String sqlDiseaseAgeGender;
    public FrmSearchPatient() {
        try
        {
            Class.forName(ConnectToDatabase.JDBC_Driver);
            con = DriverManager.getConnection(ConnectToDatabase.JDBC_Url);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        setUndecorated(true);
        SimpleButton();
        initComponents();
        //InitQuery();
        setLocationRelativeTo(null);
        ConfigControl();
        ConfigFrame();
        GetDisease();
        AddEventToAllTextField();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cmbDisease = new javax.swing.JComboBox<>();
        cmbGender = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        txtAge = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(0, 102, 255));
        jLabel1.setFont(new java.awt.Font("Microsoft Yi Baiti", 0, 55)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Find Patient");

        cmbDisease.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        cmbDisease.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a Disease" }));

        cmbGender.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        cmbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Any Gender" }));

        btnSearch.setFont(new java.awt.Font("Malgun Gothic", 0, 24)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        txtAge.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        txtAge.setText("Age");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbDisease, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbGender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(cmbDisease, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAge)
                    .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(95, 95, 95)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        InitQuery();
        try {
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if (!cmbGender.getSelectedItem().toString().equals("Any Gender")&&!txtAge.getText().equals("Age"))
            {
                rs = st.executeQuery(sqlDiseaseAgeGender);
            }
            else if (cmbGender.getSelectedItem().toString().equals("Any Gender")&&txtAge.getText().equals("Age"))
            {
                rs = st.executeQuery(sqlDiseaseOnly);
            }
            else if(!cmbGender.getSelectedItem().toString().equals("Any Gender")&&txtAge.getText().equals("Age"))
            {
                rs = st.executeQuery(sqlDiseaseAndGender);
            }
            else if (!txtAge.getText().equals("Age"))
            {
                rs = st.executeQuery(sqlDiseaseAndAge);
            }
            FrmSearchResult frm = new FrmSearchResult(rs);
            frm.setVisible(true);
            //this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(FrmSearchDoctor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmSearchPatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmSearchPatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmSearchPatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmSearchPatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmSearchPatient().setVisible(true);
            }
        });
    }
    private void SimpleButton()
    {
        try
        {
            for (UIManager.LookAndFeelInfo lnf : 
                UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(lnf.getName())) {
                UIManager.setLookAndFeel(lnf.getClassName());
                break;
                }
        }
        } catch (Exception e) { /* Lazy handling this >.> */ }
        
    }
    private void ConfigControl()
    {
        TextFieldBehavior.SetTextField(txtAge, "Age");
        cmbGender.setBackground(Color.WHITE);
        cmbDisease.setBackground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBackground(new Color(204,0,0));
        btnSearch.setForeground(new Color(255,255,255));
        cmbGender.addItem("Male");
        cmbGender.addItem("Female");
        cmbGender.addItem("Someting Else");
    }
    private void ConfigFrame()
    {
        getContentPane().setBackground(Color.WHITE);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.PINK, 2));
    }
    private void AddEventToAllTextField()
    {
        txtAge.addFocusListener(this);
        txtAge.addKeyListener(this);
    }
    private void GetDisease()
    {
        String sql = "Select * from Disease";
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next())
            {
                cmbDisease.addItem(rs.getString("Name"));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    private void InitQuery()
    {
        sqlDiseaseOnly = "select (P.[First Name] + ' '+ P.[Last Name]) as [Full Name], P.Ssn, D.Name,P.Gender, V.Comment \n" +
                        "from (Patient P inner join Visit V on P.Ssn = V.[Patient.Ssn]) \n" +
                        "inner join Disease D on D.ID = V.[Disease.ID]\n" +
                        "where D.Name = '"+cmbDisease.getSelectedItem().toString()+"'";
        sqlDiseaseAndGender = "select (P.[First Name] + ' '+ P.[Last Name]) as [Full Name], P.Ssn,P.Gender, D.Name, V.Comment \n" +
                        "from (Patient P inner join Visit V on P.Ssn = V.[Patient.Ssn]) \n" +
                        "inner join Disease D on D.ID = V.[Disease.ID]\n" +
                        "where D.Name='"+cmbDisease.getSelectedItem().toString()+"' and P.Gender = '"+cmbGender.getSelectedItem().toString()+"'";
        sqlDiseaseAndAge = "select P.Ssn,(P.[First Name] + ' '+ P.[Last Name]) as [Full Name],P.Gender,("+Year.now().getValue()+"-YEAR(P.DOB)) as [Age],D.Name, V.Comment \n" +
                        "from (Patient P inner join Visit V on P.Ssn = V.[Patient.Ssn]) \n" +
                        "inner join Disease D on D.ID = V.[Disease.ID]\n" +
                        "where D.Name='"+cmbDisease.getSelectedItem().toString()+"' and "+Year.now().getValue()+"-YEAR(P.DOB) ="+txtAge.getText();
        sqlDiseaseAgeGender = "select P.Ssn,(P.[First Name] + ' '+ P.[Last Name]) as [Full Name],P.Gender,("+Year.now().getValue()+"-YEAR(P.DOB)) as [Age],D.Name, V.Comment \n" +
                        "from (Patient P inner join Visit V on P.Ssn = V.[Patient.Ssn]) \n" +
                        "inner join Disease D on D.ID = V.[Disease.ID]\n" +
                        "where D.Name='"+cmbDisease.getSelectedItem().toString()+"' and "+Year.now().getValue()+"-YEAR(P.DOB) ="+txtAge.getText() +
                        "and P.Gender ='"+cmbGender.getSelectedItem().toString()+"'";
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbDisease;
    private javax.swing.JComboBox<String> cmbGender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtAge;
    // End of variables declaration//GEN-END:variables

    @Override
    public void focusGained(FocusEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        TextFieldBehavior.FocusGained(txtAge, "Age");
    }

    @Override
    public void focusLost(FocusEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        TextFieldBehavior.KeyTyped(txtAge, "Age");
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        TextFieldBehavior.KeyReleased(txtAge, "Age");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
