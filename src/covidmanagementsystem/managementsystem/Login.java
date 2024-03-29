package covidmanagementsystem.managementsystem;

import covidmanagementsystem.managementsystem.Account;
import covidmanagementsystem.managementsystem.SimpleMD5;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author zerotus
 */
public class Login extends javax.swing.JFrame {
    
    static String DB_URL = "jdbc:mysql://localhost:3306/ba789yyeviyfpuqmprn9";
    static String USER = "root";
    static String PASS = "zerotus";
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    JFrame jfrm;
    private String defaultPwd = "managercovid2021";
    
    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Login");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 70));

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Username");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Password");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, -1, -1));

        txtUsername.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel3.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 260, 40));

        txtPassword.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel3.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 260, 40));

        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel3.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 120, 50));

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnCancel.setText("Cancel");
        jPanel3.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 200, 120, 50));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 600, 330));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 400));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        SimpleMD5 md5 = new SimpleMD5();
        String sqlSelect = "select * from Account where username = ? and password = ?";
        try {
            conn = DriverManager.getConnection(DB_URL,USER, PASS);
            pstmt = conn.prepareStatement(sqlSelect);
            pstmt.setString(1, txtUsername.getText());
            String passwordHash = md5.hashPassword(new String(txtPassword.getPassword()));
            pstmt.setString(2, passwordHash);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int role = Integer.parseInt(rs.getString("role"));
                int status = Integer.parseInt(rs.getString("state"));
                
                if (status == 0) {
                    jfrm = new JFrame("Thông báo");
                    JOptionPane.showMessageDialog(jfrm, "Tài khoản của bạn đã bị khóa","Thông báo",JOptionPane.WARNING_MESSAGE);
                    txtUsername.setText("");
                    txtPassword.setText("");
                }
                else {
                    switch(role) {
                        case 1:
                            Admin_menu admin = new Admin_menu();
                            
                            this.hide();
                            admin.setVisible(true);
                            break;
                        case 2:
                            String defaultPwdHash = md5.hashPassword(defaultPwd);
                            if (defaultPwdHash.equals(password)) {
                                FirstLogin flogin = new FirstLogin();
                                flogin.username = username;
                                
                                this.hide();
                                flogin.setVisible(true);
                            } else {
                                Manager_menu manager = new Manager_menu();
                                manager.username = username;
                                this.hide();
                                
                                manager.setVisible(true);
                            }
                            break; 
                        case 3:
                            UserInfo user = new UserInfo(username);
                            
                            this.hide();
                            
                            user.setVisible(true);
                            break;
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Wrong password, please try again!");
                txtUsername.setText("");
                txtPassword.setText("");
            }
            
        } catch (SQLException e) {
        }
        finally{
            try {
                conn.close();
                pstmt.close();
                rs.close();
            } catch (SQLException e) {
            }
        }
    }//GEN-LAST:event_btnLoginActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
