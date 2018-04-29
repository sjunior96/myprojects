/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDI.Digital.Telas;


/**
 *
 * @author Silvio
 */

import BDI.Digital.DAL.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import BDI.Digital.Telas.TelaCadastraFuncionario;
import BDI.Digital.Telas.TelaCadastraUsuario;
import BDI.Digital.Telas.TelaCadastraBDI;

public class TelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form TelaPrincipal
     */
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    TelaCadastraUsuario usuario = null;
    TelaCadastraFuncionario funcionario = null;
    TelaCadastraBDI BDI = null;
    
    
    public TelaPrincipal() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
    private void consultaFuncionarios(){
        String sql = "SELECT matriculaFuncionario, nomeFuncionario, cargoFuncionario FROM funcionario";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                if(rs.getString(3).equals("C")){
                    BDI.cboCobradorBDI.addItem(rs.getString(1));
                }
                else{
                    BDI.cboMotoristaBDI.addItem(rs.getString(1));
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    private void consultaPrefixos(){
        String sql = "SELECT prefixoLinha FROM LINHAS";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                BDI.cboPrefixoBDI.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenCad = new javax.swing.JMenu();
        MenCadUsu = new javax.swing.JMenuItem();
        MenCadFunc = new javax.swing.JMenuItem();
        MenCadBDI = new javax.swing.JMenuItem();
        MenSobre = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Digital B.D.I - Auto Viação Goianésia");
        setMaximumSize(new java.awt.Dimension(968, 600));
        setMinimumSize(new java.awt.Dimension(968, 600));
        setResizable(false);

        desktopPane.setBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.activeTitleGradient"));
        desktopPane.setForeground(new java.awt.Color(0, 0, 204));
        desktopPane.setMaximumSize(new java.awt.Dimension(968, 579));
        desktopPane.setMinimumSize(new java.awt.Dimension(968, 579));

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 579, Short.MAX_VALUE)
        );

        MenCad.setText("Cadastrar");

        MenCadUsu.setText("Usuário");
        MenCadUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenCadUsuActionPerformed(evt);
            }
        });
        MenCad.add(MenCadUsu);

        MenCadFunc.setText("Funcionário");
        MenCadFunc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenCadFuncActionPerformed(evt);
            }
        });
        MenCad.add(MenCadFunc);

        MenCadBDI.setText("B.D.I");
        MenCadBDI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenCadBDIActionPerformed(evt);
            }
        });
        MenCad.add(MenCadBDI);

        jMenuBar1.add(MenCad);

        MenSobre.setText("Sobre");
        MenSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenSobreActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Sobre");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        MenSobre.add(jMenuItem1);

        jMenuBar1.add(MenSobre);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 968, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void MenCadBDIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenCadBDIActionPerformed
        // TODO add your handling code here:
        if(BDI == null){ //Se a tela não tiver sido criada ainda
            BDI = new TelaCadastraBDI(); //Cria nova instância da tela
            BDI.setVisible(true); //Deixa tela criada visível
            desktopPane.add(BDI); //Adiciona tela criada ao Pane
            consultaFuncionarios();
            consultaPrefixos();
        }
        else{
            if(BDI.isVisible()){ //Se a tela já tiver sido criada
                JOptionPane.showMessageDialog(null, "Já existe uma tela Cadastrar Usuário aberta!");
                BDI.pack(); //Restaura tela ao estado de maximizada caso esteja minimizada e não cria outra
            }
            else{
                BDI = new TelaCadastraBDI(); //Cria nova instância da tela
                desktopPane.add(BDI); //Adiciona tela ao Pane
                BDI.setVisible(true); //Deixa tela visível
                consultaFuncionarios();
                consultaPrefixos();
            }
        }
    }//GEN-LAST:event_MenCadBDIActionPerformed

    private void MenCadUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenCadUsuActionPerformed
        // TODO add your handling code here:
        if(usuario == null){ //Se a tela não tiver sido criada ainda
            usuario = new TelaCadastraUsuario(); //Cria nova instância da tela
            usuario.setVisible(true); //Deixa tela criada visível
            desktopPane.add(usuario); //Adiciona tela criada ao Pane
        }
        else{
            
            if(usuario.isVisible()){ //Se a tela já tiver sido criada
                JOptionPane.showMessageDialog(null, "Já existe uma tela Cadastrar Usuário aberta!");
                usuario.pack(); //Restaura ela ao estado de maximizada caso esteja minimizada e não cria outra
            }
            else{
                usuario = new TelaCadastraUsuario(); //Cria nova instância da tela
                desktopPane.add(usuario); //Adiciona tela ao Pane
                usuario.setVisible(true); //Deixa tela visível
            }
        }
    }//GEN-LAST:event_MenCadUsuActionPerformed

    private void MenCadFuncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenCadFuncActionPerformed
        // TODO add your handling code here:
        
        if(funcionario == null){
            funcionario = new TelaCadastraFuncionario(); //Cria nova instância da tela
            funcionario.setVisible(true); //Deixa tela criada visível
            desktopPane.add(funcionario); //Adiciona tela criada ao Pane
        }
        else{
            if(funcionario.isVisible()){ //Se a tela já tiver sido criada
                JOptionPane.showMessageDialog(null, "Já existe uma tela Cadastrar Usuário aberta!");
                funcionario.pack(); //Restaura tela ao estado de maximizada caso esteja minimizada e não cria outra
            }
            else{
                funcionario = new TelaCadastraFuncionario(); //Cria nova instância da tela
                desktopPane.add(funcionario); //Adiciona tela ao Pane
                funcionario.setVisible(true); //Deixa tela visível
            }
        }
    }//GEN-LAST:event_MenCadFuncActionPerformed

    private void MenSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenSobreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenSobreActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        TelaSobre sobre = new TelaSobre();
        sobre.setVisible(true);
        desktopPane.add(sobre);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu MenCad;
    private javax.swing.JMenuItem MenCadBDI;
    private javax.swing.JMenuItem MenCadFunc;
    private javax.swing.JMenuItem MenCadUsu;
    private javax.swing.JMenu MenSobre;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    // End of variables declaration//GEN-END:variables
}
