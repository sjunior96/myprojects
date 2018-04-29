/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDI.Digital.Telas;

import BDI.Digital.DAL.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Silvio
 */
public class TelaCadastraBDI extends javax.swing.JInternalFrame {

    /**
     * Creates new form TelaCadastraBDI
     */
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Double totalBDI = 0.00;
    Double valorD1 = 0.00;
    Double valorD9 = 0.00;
    Double valorFrete = 0.00;
    String BDIPesquisado;
    ArrayList<String> codigosBuscados = new ArrayList<>();
    int contadorCamposPreenchidos = 0;
    
    String formato = "#,##0.00";
    DecimalFormat formatadorDecimal = new DecimalFormat(formato);
    
    public TelaCadastraBDI() {
        initComponents();
        conexao = ModuloConexao.conector();
        btnPesquisarBDI.setEnabled(false);
        btnSalvarBDI.setEnabled(false);
        btnAlterarBDI.setEnabled(false);
        btnDeletarBDI.setEnabled(false);
        
    }
    
    private void deletaRegistros(String tabela, String codigoRegistro){
        String sql = "";
        if(tabela.equals("SERIED1")){
            sql = "DELETE FROM SERIED1 WHERE cod_registro = ?";
            
        }
        else{
            if(tabela.equals("SERIED9")){
                sql = "DELETE FROM SERIED9 WHERE cod_registro = ?";
            }
            else{
                if(tabela.equals("FRETE")){
                    sql = "DELETE FROM FRETE WHERE codFrete = ?";
                }
            }
        }
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, codigoRegistro);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro " + codigoRegistro + " da tabela " + tabela + " excluído com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falhou aqui na deletarRegistros()\n" + e);
        }
    }
    
    private void deletarBDI(){
        
        String sql = "DELETE FROM BDI WHERE codBDI = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtNumBDI.getText());
                pst.executeUpdate();
                deletaRegistros("SERIED1", codigosBuscados.get(0));
                deletaRegistros("SERIED9", codigosBuscados.get(1));
                deletaRegistros("FRETE", codigosBuscados.get(2));
                JOptionPane.showMessageDialog(null, "BDI deletado com sucesso!");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
    }
    
    private void alterarTaloesD1(){
        String sql = "UPDATE SERIED1 SET iniciantePassagem = ?, encerrantePassagem = ?, valorRegistro = ? WHERE cod_registro = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtInicianteD1.getText());
            pst.setString(2, txtEncerranteD1.getText());
            pst.setString(3, txtValorD1.getText());
            pst.setString(4, codigosBuscados.get(0));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tabela de Talões D1 atualizada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void alterarTaloesD9(){
        String sql = "UPDATE SERIED9 SET iniciantePassagem = ?, encerrantePassagem = ?, valorRegistro = ? WHERE cod_registro = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtInicianteD9.getText());
            pst.setString(2, txtEncerranteD9.getText());
            pst.setString(3, txtValorD9.getText());
            pst.setString(4, codigosBuscados.get(1));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tabela de Talões D9 atualizada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void alterarFretes(){
        String sql = "UPDATE FRETE SET inicianteFrete = ?, encerranteFrete = ?, valorFrete = ? WHERE codFrete = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtInicianteFrete.getText());
            pst.setString(2, txtEncerranteFrete.getText());
            pst.setString(3, txtValorFrete.getText());
            pst.setString(4, codigosBuscados.get(2));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tabela de Fretes atualizada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void alteraBDI(){
        String sql = "UPDATE BDI SET codBDI = ?, dataBDI = ?, horarioBDI = ?, prefixoLinha = ?, matriculaCobradorBDI = ?, "
                + "matriculaMotoristaBDI = ?, codRegistroD1 = ?, codRegistroD9 = ?, codFrete = ?";
        try {
            DateFormat formatador = new SimpleDateFormat("dd-MM-yyyy");
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNumBDI.getText());
            pst.setString(2, formatador.format(calendarioBDI.getDate()));
            pst.setString(3, cboHorarioBDI.getSelectedItem().toString());
            pst.setString(4, cboPrefixoBDI.getSelectedItem().toString());
            pst.setString(5, cboCobradorBDI.getSelectedItem().toString());
            pst.setString(6, cboMotoristaBDI.getSelectedItem().toString());
            //pst.setString(7, txt.getText());
            //pst.setString(8, txtNumBDI.getText());
            //pst.setString(9, txtNumBDI.getText());
            pst.executeUpdate();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public boolean validaCampos(){
        
        if(!txtInicianteD1.getText().isEmpty() &&
                !txtEncerranteD1.getText().isEmpty() &&
                !txtValorD1.getText().isEmpty() &&
                !txtInicianteD9.getText().isEmpty() &&
                !txtEncerranteD9.getText().isEmpty() &&
                !txtValorD9.getText().isEmpty() &&
                !txtInicianteFrete.getText().isEmpty() &&
                !txtEncerranteFrete.getText().isEmpty() &&
                !txtValorFrete.getText().isEmpty() &&
                !txtNumBDI.getText().isEmpty() &&
                (calendarioBDI.getDate().before(new Date()) == true) &&
                !cboCobradorBDI.getSelectedItem().toString().equals("Selecione") &&
                !cboMotoristaBDI.getSelectedItem().toString().equals("Selecione") &&
                !cboPrefixoBDI.getSelectedItem().toString().equals("Selecione")){
            return true;
        }
        else{
            return false;
        }
    }
    
    public int cadastraFrete(){
        //rs = null;
        ResultSet rsFretes;
        String sql = "INSERT INTO FRETE(inicianteFrete, encerranteFrete,"
                                + " valorFrete) VALUES(?,?,?)";
        try {
            pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, txtInicianteFrete.getText());
            pst.setString(2, txtEncerranteFrete.getText());
            pst.setString(3, txtValorFrete.getText());
            pst.executeUpdate();
            rsFretes = pst.getGeneratedKeys();
            rsFretes.next();
            JOptionPane.showMessageDialog(null, "Os dados foram cadastrados com sucesso na tabela de Fretes!");
            codigosBuscados.add(Integer.toString(rsFretes.getInt(1)));
            return rsFretes.getInt(1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO CADASTRAR OS FRETES!!!!!!!!!!!!!" + e);
        }
        return -1;
    }
    
    public int cadastraTalaoD1(){
        String sql = "INSERT INTO SERIED1(iniciantePassagem, encerrantePassagem,"
                                + " valorRegistro) VALUES(?,?,?)";
        
        try {
            pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, txtInicianteD1.getText());
            pst.setString(2, txtEncerranteD1.getText());
            pst.setString(3, txtValorD1.getText());
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            JOptionPane.showMessageDialog(null, "Os dados foram cadastrados com sucesso na tabelas de Talões D1!");
            //JOptionPane.showMessageDialog(null, rs.getInt(1));
            codigosBuscados.add(Integer.toString(rs.getInt(1)));
            return rs.getInt(1);
        } catch (Exception e) {
            if(e.toString().contains("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry ")){
                JOptionPane.showMessageDialog(null, "Verifique iniciante possivelmente já utilizado!");
            }
            else{
                JOptionPane.showMessageDialog(null, e);
                        
            }
        }
        return -1;
    }
    
    public int cadastraTalaoD9(){
        String sql = "INSERT INTO SERIED9(iniciantePassagem, encerrantePassagem,"
                                + " valorRegistro) VALUES(?,?,?)";
        try {
            pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, txtInicianteD9.getText());
            pst.setString(2, txtEncerranteD9.getText());
            pst.setString(3, txtValorD9.getText());
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            JOptionPane.showMessageDialog(null, "Os dados foram cadastrados com sucesso na tabela de Talões D9!");
            codigosBuscados.add(Integer.toString(rs.getInt(1)));
            return rs.getInt(1);
        } catch (Exception e) {
            if(e.toString().contains("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry ")){
                JOptionPane.showMessageDialog(null, "Verifique iniciante possivelmente já utilizado!");
            }
            else{
                JOptionPane.showMessageDialog(null, "Falha no Cadastra Talao D9" + e);
                        
            }
        }
        return -1;
    }
    
    public void cadastraBDI(){
        String sqlBDI = "INSERT INTO BDI(codBDI, dataBDI, horarioBDI, "
                + "prefixoLinha, matriculaCobradorBDI, matriculaMotoristaBDI, codRegistroD1,"
                + "codRegistroD9, codFrete) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            DateFormat formatador = new SimpleDateFormat("dd-MM-yyyy");
            pst = conexao.prepareStatement(sqlBDI);
            pst.setString(1, txtNumBDI.getText());
            //JOptionPane.showMessageDialog(null, formatador.format(calendarioBDI.getDate()));
            pst.setString(2, formatador.format(calendarioBDI.getDate()));
            pst.setString(3, cboHorarioBDI.getSelectedItem().toString());
            pst.setString(4, cboPrefixoBDI.getSelectedItem().toString());
            pst.setString(5, cboCobradorBDI.getSelectedItem().toString());
            pst.setString(6, cboMotoristaBDI.getSelectedItem().toString());
            pst.setString(7, codigosBuscados.get(0));
            pst.setString(8, codigosBuscados.get(1));
            pst.setString(9, codigosBuscados.get(2));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "BDI cadastrado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void consultaItinerario(){ 
        ResultSet rsItinerario;
        //String sql = "SELECT itinerarioLinha FROM linhas WHERE prefixoLinha = ?";
        String sql = "SELECT LINHAS.itinerarioLinha, HORARIOS.horario FROM linhas \n" +
                        "INNER JOIN HORARIOS ON HORARIOS.prefixoLinha = ?\n" +
                        "WHERE LINHAS.prefixoLinha = ?";
        if(!cboPrefixoBDI.getSelectedItem().toString().equals("Selecione")){
            try {
                cboHorarioBDI.removeAllItems();
                pst = conexao.prepareStatement(sql);
                pst.setString(1, cboPrefixoBDI.getSelectedItem().toString());
                pst.setString(2, cboPrefixoBDI.getSelectedItem().toString());
                rsItinerario = pst.executeQuery();
                while(rsItinerario.next()){
                    cboHorarioBDI.addItem(rsItinerario.getString(2));
                    txtItinerarioBDI.setText(rsItinerario.getString(1));
                }
            } catch (Exception e) {
                //Encontrei o erro atual
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Falhou aqui\n" + e);
            }
        }
    }
    
    private void consultaHorarios(){
        rs = null;
        cboHorarioBDI.removeAllItems();
        cboHorarioBDI.addItem("Selecione");
        String sql = "SELECT horario FROM HORARIOS WHERE prefixoLinha = ?";
        if(!cboPrefixoBDI.getSelectedItem().toString().equals("Selecione")){
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, cboPrefixoBDI.getSelectedItem().toString());
                rs = pst.executeQuery();
                while(rs.next()){
                    cboHorarioBDI.addItem(rs.getString(1));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    private void consultaFuncionarios(String comboBox){
        String sql = "SELECT nomeFuncionario, cargoFuncionario FROM funcionario WHERE matriculaFuncionario = ?";
        try {
            pst = conexao.prepareStatement(sql);
            if(comboBox.equals("cobrador")){
                pst.setString(1, cboCobradorBDI.getSelectedItem().toString());
            }
            else{
                if(comboBox.equals("motorista")){
                    pst.setString(1, cboMotoristaBDI.getSelectedItem().toString());
                }
            }
            rs = pst.executeQuery();
            if(rs.next()){
                if(rs.getString(2).equals("C")){
                    txtNomeCobradorBDI.setText(rs.getString(1));
                }
                else{
                    if(rs.getString(2).equals("M")){
                        txtNomeMotoristaBDI.setText(rs.getString(1));
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void consultaBDI(){
        ResultSet rs2;
        String sql = "SELECT BDI.codBDI NºBDI, BDI.dataBDI DIA, \n" +
            "BDI.horarioBDI HORARIO, \n" +
            "BDI.prefixoLinha PREFIXO, L.itinerarioLinha ITINERARIO, \n" +
            "BDI.matriculaCobradorBDI MATCOBRADOR, F.nomeFuncionario NOMECOBRADOR, \n" +
            "BDI.matriculaMotoristaBDI MATMOTORISTA, FUNCIONARIO.nomeFuncionario NOMEMOTORISTA,  \n" +
            "BDI.codRegistroD1 CD1, SERIED1.iniciantePassagem INICIOD1, SERIED1.encerrantePassagem FIMD1, SERIED1.valorRegistro VALORD1, \n" +
            "BDI.codRegistroD9 CD9, SERIED9.iniciantePassagem INICIOD9, SERIED9.encerrantePassagem FIMD9, SERIED9.valorRegistro VALORD9, \n" +
            "BDI.codFrete CDFRETE, FRETE.inicianteFrete INICIOFRETE, FRETE.encerranteFrete FIMFRETE, FRETE.valorFrete VALORFRETE FROM BDI\n" +
            "INNER JOIN HORARIOS ON HORARIOS.horario = BDI.horarioBDI AND horarios.prefixoLinha = BDI.prefixoLinha\n" +
            "INNER JOIN LINHAS L ON L.prefixoLinha = HORARIOS.prefixoLinha\n" +
            "INNER JOIN FUNCIONARIO F ON F.matriculaFuncionario = BDI.matriculaCobradorBDI\n" +
            "INNER JOIN FUNCIONARIO ON FUNCIONARIO.matriculaFuncionario = BDI.matriculaMotoristaBDI\n" +
            "INNER JOIN SERIED1 ON SERIED1.cod_registro = BDI.codRegistroD1\n" +
            "INNER JOIN SERIED9 ON SERIED9.cod_registro = BDI.codRegistroD9\n" +
            "INNER JOIN FRETE ON FRETE.codFrete = BDI.codFrete\n" +
            "	WHERE BDI.codBDI = ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNumBDI.getText());
            rs2 = pst.executeQuery();
            if(rs2.next()){
                SimpleDateFormat formatadorData = new SimpleDateFormat("dd-MM-yyyy");
                calendarioBDI.setDate(formatadorData.parse(rs2.getString(2)));
                
                cboHorarioBDI.setSelectedItem(rs2.getString(3));
                cboPrefixoBDI.setSelectedItem(rs2.getString(4));
                txtItinerarioBDI.setText(rs2.getString(5));
                cboCobradorBDI.setSelectedItem(rs2.getString(6));
                txtNomeCobradorBDI.setText(rs2.getString(7));
                cboMotoristaBDI.setSelectedItem(rs2.getString(8));
                txtNomeMotoristaBDI.setText(rs2.getString(9));
                txtInicianteD1.setText(rs2.getString(11));
                txtEncerranteD1.setText(rs2.getString(12));
                txtValorD1.setText(rs2.getString(13));
                txtInicianteD9.setText(rs2.getString(15));
                txtEncerranteD9.setText(rs2.getString(16));
                txtValorD9.setText(rs2.getString(17));
                txtInicianteFrete.setText(rs2.getString(19));
                txtEncerranteFrete.setText(rs2.getString(20));
                txtValorFrete.setText(rs2.getString(21));
                
                //Salvo os códigos buscados
                codigosBuscados.clear();
                codigosBuscados.add(rs2.getString(10));
                codigosBuscados.add(rs2.getString(14));
                codigosBuscados.add(rs2.getString(18));
                
                /*Atualiza o valor mostrado no Total BDI com os valores lidos somados e formatados com vírgula
                no lugar do ponto*/
                valorD1 = Double.parseDouble(rs2.getString(13));
                valorD9 = Double.parseDouble(rs2.getString(17));
                valorFrete = Double.parseDouble(rs2.getString(21));
                totalBDI = valorD1 + valorD9 + valorFrete;
                lblTotalBDI.setText(formatadorDecimal.format(totalBDI).toString());
                btnDeletarBDI.setEnabled(true);
                btnAlterarBDI.setEnabled(true);
                BDIPesquisado = txtNumBDI.getText();
            }
            else{
                JOptionPane.showMessageDialog(null, "BDI não cadastrado!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + "\nFalhou aqui!");
        }
    }
    
    private void confirmaDecisao(String mensagemParaUsuario){
        if(JOptionPane.showConfirmDialog(null, mensagemParaUsuario, "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0){
            JOptionPane.showMessageDialog(null, "Cadastro atualizado com sucesso");
        }
        else{
            JOptionPane.showMessageDialog(null, "Atualização cancelada!");
        }
    }
    
    private boolean checaCodBDI(){
        String sql = "SELECT * FROM BDI WHERE codBDI = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNumBDI.getText());
            rs = pst.executeQuery();
            if(rs.next()){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return true;
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNomeMotoristaBDI = new javax.swing.JTextField();
        txtItinerarioBDI = new javax.swing.JTextField();
        cboHorarioBDI = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtNomeCobradorBDI = new javax.swing.JTextField();
        cboPrefixoBDI = new javax.swing.JComboBox<>();
        cboMotoristaBDI = new javax.swing.JComboBox<>();
        cboCobradorBDI = new javax.swing.JComboBox<>();
        calendarioBDI = new com.toedter.calendar.JCalendar();
        txtNumBDI = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        txtInicianteD1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtEncerranteD1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtInicianteD9 = new javax.swing.JTextField();
        txtEncerranteD9 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        txtValorD1 = new javax.swing.JTextField();
        txtValorD9 = new javax.swing.JTextField();
        txtInicianteFrete = new javax.swing.JTextField();
        txtEncerranteFrete = new javax.swing.JTextField();
        txtValorFrete = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblTotalBDI = new javax.swing.JLabel();
        btnLimparFormBDI = new javax.swing.JButton();
        btnPesquisarBDI = new javax.swing.JButton();
        btnAlterarBDI = new javax.swing.JButton();
        btnDeletarBDI = new javax.swing.JButton();
        btnSalvarBDI = new javax.swing.JButton();

        setClosable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(940, 220));

        jLabel1.setText("Viação Goianésia Ltda.");

        jLabel2.setText("Administração Total de Transportes");

        jLabel3.setText("Infobus Comércio e Serviços Ltda.");

        txtNomeMotoristaBDI.setEditable(false);

        txtItinerarioBDI.setEditable(false);

        cboHorarioBDI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cboHorarioBDI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHorarioBDIActionPerformed(evt);
            }
        });

        jLabel10.setText("-");

        jLabel20.setText("Nº BDI");

        jLabel11.setText("-");

        jLabel12.setText("-");

        jLabel13.setText("-");

        jLabel6.setText("Cobrador");

        jLabel7.setText("Motorista");

        jLabel8.setText("Linha");

        jLabel19.setText("Horário:");

        txtNomeCobradorBDI.setEditable(false);

        cboPrefixoBDI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cboPrefixoBDI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPrefixoBDIActionPerformed(evt);
            }
        });

        cboMotoristaBDI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cboMotoristaBDI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMotoristaBDIActionPerformed(evt);
            }
        });

        cboCobradorBDI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        cboCobradorBDI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCobradorBDIActionPerformed(evt);
            }
        });

        txtNumBDI.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNumBDICaretUpdate(evt);
            }
        });
        txtNumBDI.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumBDIFocusGained(evt);
            }
        });
        txtNumBDI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumBDIKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboMotoristaBDI, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboCobradorBDI, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNumBDI))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel11))
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboPrefixoBDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(txtItinerarioBDI, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cboHorarioBDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtNomeCobradorBDI)
                                    .addComponent(txtNomeMotoristaBDI))))
                        .addGap(18, 18, 18)
                        .addComponent(calendarioBDI, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97)
                        .addComponent(jLabel15)
                        .addGap(111, 111, 111))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel20)
                                        .addComponent(txtNumBDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel8)
                                        .addComponent(txtItinerarioBDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cboPrefixoBDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel19)
                                        .addComponent(cboHorarioBDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtNomeCobradorBDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(cboCobradorBDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtNomeMotoristaBDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12)
                                    .addComponent(cboMotoristaBDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13))
                            .addComponent(jLabel15))
                        .addContainerGap(23, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(calendarioBDI, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bilhetes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N
        jPanel2.setMaximumSize(new java.awt.Dimension(928, 207));
        jPanel2.setMinimumSize(new java.awt.Dimension(928, 207));

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Série");

        txtInicianteD1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtInicianteD1CaretUpdate(evt);
            }
        });
        txtInicianteD1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtInicianteD1FocusGained(evt);
            }
        });
        txtInicianteD1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInicianteD1KeyTyped(evt);
            }
        });

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Início");

        txtEncerranteD1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtEncerranteD1CaretUpdate(evt);
            }
        });
        txtEncerranteD1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEncerranteD1FocusGained(evt);
            }
        });
        txtEncerranteD1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEncerranteD1KeyTyped(evt);
            }
        });

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Final");

        txtInicianteD9.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtInicianteD9CaretUpdate(evt);
            }
        });
        txtInicianteD9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtInicianteD9FocusGained(evt);
            }
        });
        txtInicianteD9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInicianteD9KeyTyped(evt);
            }
        });

        txtEncerranteD9.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtEncerranteD9CaretUpdate(evt);
            }
        });
        txtEncerranteD9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEncerranteD9FocusGained(evt);
            }
        });
        txtEncerranteD9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEncerranteD9KeyTyped(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "D - 1" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "D - 9" }));

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Valor acertado");

        txtValorD1.setText("0.00");
        txtValorD1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtValorD1CaretUpdate(evt);
            }
        });
        txtValorD1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorD1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorD1FocusLost(evt);
            }
        });
        txtValorD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorD1ActionPerformed(evt);
            }
        });
        txtValorD1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValorD1KeyTyped(evt);
            }
        });

        txtValorD9.setText("0.00");
        txtValorD9.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtValorD9CaretUpdate(evt);
            }
        });
        txtValorD9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorD9FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorD9FocusLost(evt);
            }
        });
        txtValorD9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValorD9KeyTyped(evt);
            }
        });

        txtInicianteFrete.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtInicianteFreteCaretUpdate(evt);
            }
        });
        txtInicianteFrete.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtInicianteFreteFocusGained(evt);
            }
        });
        txtInicianteFrete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInicianteFreteActionPerformed(evt);
            }
        });
        txtInicianteFrete.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInicianteFreteKeyTyped(evt);
            }
        });

        txtEncerranteFrete.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtEncerranteFreteCaretUpdate(evt);
            }
        });
        txtEncerranteFrete.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEncerranteFreteFocusGained(evt);
            }
        });
        txtEncerranteFrete.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEncerranteFreteKeyTyped(evt);
            }
        });

        txtValorFrete.setText("0.00");
        txtValorFrete.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtValorFreteCaretUpdate(evt);
            }
        });
        txtValorFrete.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorFreteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorFreteFocusLost(evt);
            }
        });
        txtValorFrete.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValorFreteKeyTyped(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FRETE" }));

        jLabel4.setText("Valor Total");

        jLabel14.setText("R$");

        lblTotalBDI.setText("0,00");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, 0, 110, Short.MAX_VALUE))
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtInicianteFrete)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(txtInicianteD9)
                    .addComponent(txtInicianteD1))
                .addGap(78, 78, 78)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtEncerranteD9)
                    .addComponent(txtEncerranteD1)
                    .addComponent(txtEncerranteFrete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addGap(84, 84, 84)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtValorD9)
                    .addComponent(txtValorD1)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtValorFrete, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalBDI, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtValorD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(lblTotalBDI))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValorD9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel26)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel24)
                                .addComponent(jLabel25)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEncerranteD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtInicianteD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEncerranteD9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtInicianteD9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtInicianteFrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEncerranteFrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtValorFrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        btnLimparFormBDI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BDI/Digital/Icones/if_CCleaner_33788.png"))); // NOI18N
        btnLimparFormBDI.setToolTipText("Limpar Formulário");
        btnLimparFormBDI.setBorderPainted(false);
        btnLimparFormBDI.setContentAreaFilled(false);
        btnLimparFormBDI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimparFormBDI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparFormBDIActionPerformed(evt);
            }
        });

        btnPesquisarBDI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BDI/Digital/Icones/if_file_search_48764.png"))); // NOI18N
        btnPesquisarBDI.setToolTipText("Pesquisar BDI");
        btnPesquisarBDI.setBorderPainted(false);
        btnPesquisarBDI.setContentAreaFilled(false);
        btnPesquisarBDI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPesquisarBDI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarBDIActionPerformed(evt);
            }
        });

        btnAlterarBDI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BDI/Digital/Icones/if_file_edit_48763.png"))); // NOI18N
        btnAlterarBDI.setToolTipText("Salvar alterações");
        btnAlterarBDI.setBorderPainted(false);
        btnAlterarBDI.setContentAreaFilled(false);
        btnAlterarBDI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAlterarBDI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarBDIActionPerformed(evt);
            }
        });

        btnDeletarBDI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BDI/Digital/Icones/if_file_delete_48762.png"))); // NOI18N
        btnDeletarBDI.setToolTipText("Deletar BDI");
        btnDeletarBDI.setBorderPainted(false);
        btnDeletarBDI.setContentAreaFilled(false);
        btnDeletarBDI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletarBDI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarBDIActionPerformed(evt);
            }
        });

        btnSalvarBDI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BDI/Digital/Icones/if_file_add_48761.png"))); // NOI18N
        btnSalvarBDI.setToolTipText("Cadastrar BDI");
        btnSalvarBDI.setBorderPainted(false);
        btnSalvarBDI.setContentAreaFilled(false);
        btnSalvarBDI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvarBDI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarBDIActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSalvarBDI, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisarBDI, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAlterarBDI, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeletarBDI, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimparFormBDI, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 928, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSalvarBDI)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnAlterarBDI)
                        .addComponent(btnPesquisarBDI)
                        .addComponent(btnDeletarBDI)
                        .addComponent(btnLimparFormBDI)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboHorarioBDIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHorarioBDIActionPerformed
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado) && validaCampos() == true){
            btnAlterarBDI.setEnabled(true);

        }
        else{
            btnAlterarBDI.setEnabled(false);

        }

        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
            //btnAlterarUsu.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
            //btnAlterarUsu.setEnabled(false);
        }
    }//GEN-LAST:event_cboHorarioBDIActionPerformed

    private void cboPrefixoBDIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPrefixoBDIActionPerformed
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado) && validaCampos() == true){
            btnAlterarBDI.setEnabled(true);
        }
        else{
            btnAlterarBDI.setEnabled(false);
        }

        if(cboPrefixoBDI.getSelectedItem().toString().equals("Selecione")){
            cboHorarioBDI.removeAllItems();
            cboHorarioBDI.addItem("Selecione");
        }
        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
        }
        consultaItinerario();
        //consultaHorarios();
    }//GEN-LAST:event_cboPrefixoBDIActionPerformed

    private void cboMotoristaBDIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMotoristaBDIActionPerformed
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado) && validaCampos() == true){
            btnAlterarBDI.setEnabled(true);
        }
        else{
            btnAlterarBDI.setEnabled(false);
        }

        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
        }
        consultaFuncionarios("motorista");
    }//GEN-LAST:event_cboMotoristaBDIActionPerformed

    private void cboCobradorBDIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCobradorBDIActionPerformed
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado) && validaCampos() == true){
            btnAlterarBDI.setEnabled(true);
        }
        else{
            btnAlterarBDI.setEnabled(false);
        }
        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
        }
        consultaFuncionarios("cobrador");
    }//GEN-LAST:event_cboCobradorBDIActionPerformed

    private void txtNumBDICaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNumBDICaretUpdate
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado)){
            btnDeletarBDI.setEnabled(true);
            if(validaCampos() == true){
                btnAlterarBDI.setEnabled(true);
            }
            else{
                btnAlterarBDI.setEnabled(false);
            }
        }
        else{
            btnAlterarBDI.setEnabled(false);
            btnDeletarBDI.setEnabled(false);
        }

        if(!txtNumBDI.getText().isEmpty()){
            btnPesquisarBDI.setEnabled(true);

        }
        else{
            btnPesquisarBDI.setEnabled(false);
        }

        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
            //btnAlterarUsu.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
            //btnAlterarUsu.setEnabled(false);
        }
    }//GEN-LAST:event_txtNumBDICaretUpdate

    private void txtNumBDIFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumBDIFocusGained
        // TODO add your handling code here:
        txtNumBDI.selectAll();
    }//GEN-LAST:event_txtNumBDIFocusGained

    private void txtNumBDIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumBDIKeyTyped
        // TODO add your handling code here:
        String caracteresAceitos="0987654321";
        if(!caracteresAceitos.contains(evt.getKeyChar() + "")){
            evt.consume();
        }
    }//GEN-LAST:event_txtNumBDIKeyTyped

    private void txtInicianteD1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtInicianteD1CaretUpdate
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado) && validaCampos() == true){
            btnAlterarBDI.setEnabled(true);
        }
        else{
            btnAlterarBDI.setEnabled(false);
        }

        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
            //btnAlterarUsu.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
            //btnAlterarUsu.setEnabled(false);
        }
    }//GEN-LAST:event_txtInicianteD1CaretUpdate

    private void txtInicianteD1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInicianteD1FocusGained
        // TODO add your handling code here:
        txtInicianteD1.selectAll();
    }//GEN-LAST:event_txtInicianteD1FocusGained

    private void txtInicianteD1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInicianteD1KeyTyped
        // TODO add your handling code here:
        String caracteresAceitos="0987654321";
        if(!caracteresAceitos.contains(evt.getKeyChar() + "")){
            evt.consume();
        }
    }//GEN-LAST:event_txtInicianteD1KeyTyped

    private void txtEncerranteD1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtEncerranteD1CaretUpdate
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado) && validaCampos() == true){
            btnAlterarBDI.setEnabled(true);
        }
        else{
            btnAlterarBDI.setEnabled(false);
        }

        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
            //btnAlterarUsu.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
            //btnAlterarUsu.setEnabled(false);
        }
    }//GEN-LAST:event_txtEncerranteD1CaretUpdate

    private void txtEncerranteD1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEncerranteD1FocusGained
        // TODO add your handling code here:
        txtEncerranteD1.selectAll();
    }//GEN-LAST:event_txtEncerranteD1FocusGained

    private void txtEncerranteD1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEncerranteD1KeyTyped
        // TODO add your handling code here:
        String caracteresAceitos="0987654321";
        if(!caracteresAceitos.contains(evt.getKeyChar() + "")){
            evt.consume();
        }
    }//GEN-LAST:event_txtEncerranteD1KeyTyped

    private void txtInicianteD9CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtInicianteD9CaretUpdate
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado) && validaCampos() == true){
            btnAlterarBDI.setEnabled(true);
        }
        else{
            btnAlterarBDI.setEnabled(false);
        }

        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
            //btnAlterarUsu.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
            //btnAlterarUsu.setEnabled(false);
        }

    }//GEN-LAST:event_txtInicianteD9CaretUpdate

    private void txtInicianteD9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInicianteD9FocusGained
        // TODO add your handling code here:
        txtInicianteD9.selectAll();
    }//GEN-LAST:event_txtInicianteD9FocusGained

    private void txtInicianteD9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInicianteD9KeyTyped
        // TODO add your handling code here:
        String caracteresAceitos="0987654321";
        if(!caracteresAceitos.contains(evt.getKeyChar() + "")){
            evt.consume();
        }
    }//GEN-LAST:event_txtInicianteD9KeyTyped

    private void txtEncerranteD9CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtEncerranteD9CaretUpdate
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado) && validaCampos() == true){
            btnAlterarBDI.setEnabled(true);
        }
        else{
            btnAlterarBDI.setEnabled(false);
        }
        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
            //btnAlterarUsu.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
            //btnAlterarUsu.setEnabled(false);
        }
    }//GEN-LAST:event_txtEncerranteD9CaretUpdate

    private void txtEncerranteD9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEncerranteD9FocusGained
        // TODO add your handling code here:
        txtEncerranteD9.selectAll();
    }//GEN-LAST:event_txtEncerranteD9FocusGained

    private void txtEncerranteD9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEncerranteD9KeyTyped
        // TODO add your handling code here:
        String caracteresAceitos="0987654321";
        if(!caracteresAceitos.contains(evt.getKeyChar() + "")){
            evt.consume();
        }
    }//GEN-LAST:event_txtEncerranteD9KeyTyped

    private void txtValorD1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtValorD1CaretUpdate
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado) && validaCampos() == true){
            btnAlterarBDI.setEnabled(true);
        }
        else{
            btnAlterarBDI.setEnabled(false);
        }
        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
            //btnAlterarUsu.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
            //btnAlterarUsu.setEnabled(false);
        }
    }//GEN-LAST:event_txtValorD1CaretUpdate

    private void txtValorD1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorD1FocusGained
        // TODO add your handling code here:
        //Seleciona todo o conteudo do campo quando ele for selecionado para uso
        txtValorD1.selectAll();
    }//GEN-LAST:event_txtValorD1FocusGained

    private void txtValorD1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorD1FocusLost
        // TODO add your handling code here:
        try {
            valorD1 = Double.parseDouble(txtValorD1.getText());
            totalBDI = valorD1 + valorD9 + valorFrete;
            lblTotalBDI.setText(formatadorDecimal.format(totalBDI).toString());
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, e);
        }
        //JOptionPane.showMessageDialog(null, "Foco perdido!");
    }//GEN-LAST:event_txtValorD1FocusLost

    private void txtValorD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorD1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorD1ActionPerformed

    private void txtValorD1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorD1KeyTyped
        // TODO add your handling code here:
        String caracteresAceitos="0987654321.,";
        if(!caracteresAceitos.contains(evt.getKeyChar() + "")){
            evt.consume();
        }
    }//GEN-LAST:event_txtValorD1KeyTyped

    private void txtValorD9CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtValorD9CaretUpdate
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado) && validaCampos() == true){
            btnAlterarBDI.setEnabled(true);
        }
        else{
            btnAlterarBDI.setEnabled(false);
        }

        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
            //btnAlterarUsu.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
            //btnAlterarUsu.setEnabled(false);
        }
    }//GEN-LAST:event_txtValorD9CaretUpdate

    private void txtValorD9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorD9FocusGained
        // TODO add your handling code here:
        //Seleciona todo o conteudo do campo quando ele for selecionado para uso
        txtValorD9.selectAll();
    }//GEN-LAST:event_txtValorD9FocusGained

    private void txtValorD9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorD9FocusLost
        // TODO add your handling code here:
        try {
            valorD9 = Double.parseDouble(txtValorD9.getText());
            totalBDI = valorD1 + valorD9 + valorFrete;
            lblTotalBDI.setText(formatadorDecimal.format(totalBDI).toString());
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, e);
        }
        //JOptionPane.showMessageDialog(null, "Foco perdido!");
    }//GEN-LAST:event_txtValorD9FocusLost

    private void txtValorD9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorD9KeyTyped
        // TODO add your handling code here:
        String caracteresAceitos="0987654321.";
        if(!caracteresAceitos.contains(evt.getKeyChar() + "")){
            evt.consume();
        }
    }//GEN-LAST:event_txtValorD9KeyTyped

    private void txtInicianteFreteCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtInicianteFreteCaretUpdate
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado) && validaCampos() == true){
            btnAlterarBDI.setEnabled(true);
        }
        else{
            btnAlterarBDI.setEnabled(false);
        }

        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
            //btnAlterarUsu.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
            //btnAlterarUsu.setEnabled(false);
        }
    }//GEN-LAST:event_txtInicianteFreteCaretUpdate

    private void txtInicianteFreteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInicianteFreteFocusGained
        // TODO add your handling code here:
        txtInicianteFrete.selectAll();
    }//GEN-LAST:event_txtInicianteFreteFocusGained

    private void txtInicianteFreteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInicianteFreteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInicianteFreteActionPerformed

    private void txtInicianteFreteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInicianteFreteKeyTyped
        // TODO add your handling code here:
        String caracteresAceitos="0987654321";
        if(!caracteresAceitos.contains(evt.getKeyChar() + "")){
            evt.consume();
        }
    }//GEN-LAST:event_txtInicianteFreteKeyTyped

    private void txtEncerranteFreteCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtEncerranteFreteCaretUpdate
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado) && validaCampos() == true){
            btnAlterarBDI.setEnabled(true);
        }
        else{
            btnAlterarBDI.setEnabled(false);
        }
        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
            //btnAlterarUsu.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
            //btnAlterarUsu.setEnabled(false);
        }
    }//GEN-LAST:event_txtEncerranteFreteCaretUpdate

    private void txtEncerranteFreteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEncerranteFreteFocusGained
        // TODO add your handling code here:
        txtEncerranteFrete.selectAll();
    }//GEN-LAST:event_txtEncerranteFreteFocusGained

    private void txtEncerranteFreteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEncerranteFreteKeyTyped
        // TODO add your handling code here:
        String caracteresAceitos="0987654321";
        if(!caracteresAceitos.contains(evt.getKeyChar() + "")){
            evt.consume();
        }
    }//GEN-LAST:event_txtEncerranteFreteKeyTyped

    private void txtValorFreteCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtValorFreteCaretUpdate
        // TODO add your handling code here:
        if(txtNumBDI.getText().equals(BDIPesquisado) && validaCampos() == true){
            btnAlterarBDI.setEnabled(true);
        }
        else{
            btnAlterarBDI.setEnabled(false);
        }

        if(validaCampos() == true){
            btnSalvarBDI.setEnabled(true);
            //btnAlterarUsu.setEnabled(true);
        }
        else{
            btnSalvarBDI.setEnabled(false);
            //btnAlterarUsu.setEnabled(false);
        }
    }//GEN-LAST:event_txtValorFreteCaretUpdate

    private void txtValorFreteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFreteFocusGained
        // TODO add your handling code here:
        //Seleciona todo o conteudo do campo quando ele for selecionado para uso
        txtValorFrete.selectAll();
    }//GEN-LAST:event_txtValorFreteFocusGained

    private void txtValorFreteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFreteFocusLost
        // TODO add your handling code here:
        try {
            valorFrete = Double.parseDouble(txtValorFrete.getText());
            totalBDI = valorD1 + valorD9 + valorFrete;
            lblTotalBDI.setText(formatadorDecimal.format(totalBDI).toString());
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txtValorFreteFocusLost

    private void txtValorFreteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorFreteKeyTyped
        // TODO add your handling code here:
        String caracteresAceitos="0987654321.";
        if(!caracteresAceitos.contains(evt.getKeyChar() + "")){
            evt.consume();
        }
    }//GEN-LAST:event_txtValorFreteKeyTyped

    private void btnLimparFormBDIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparFormBDIActionPerformed
        // TODO add your handling code here:
        btnPesquisarBDI.setEnabled(false);
        txtEncerranteD1.setText(null);
        txtEncerranteD9.setText(null);
        txtEncerranteFrete.setText(null);
        txtInicianteD1.setText(null);
        txtInicianteD9.setText(null);
        txtInicianteFrete.setText(null);
        txtItinerarioBDI.setText(null);
        txtNomeCobradorBDI.setText(null);
        txtNomeMotoristaBDI.setText(null);
        txtNumBDI.setText(null);
        txtValorD1.setText(null);
        txtValorD9.setText(null);
        txtValorFrete.setText(null);
        cboCobradorBDI.setSelectedItem("Selecione");
        cboHorarioBDI.setSelectedItem("Selecione");
        cboMotoristaBDI.setSelectedItem("Selecione");
        cboPrefixoBDI.setSelectedItem("Selecione");
        valorD1 = 0.00;
        valorD9 = 0.00;
        valorFrete = 0.00;
        btnAlterarBDI.setEnabled(false);
        btnDeletarBDI.setEnabled(false);
        BDIPesquisado = "nulo";
        //String formato = "#,##0.00";
        //DecimalFormat formatadorDecimal = new DecimalFormat(formato);

        lblTotalBDI.setText(String.valueOf(formatadorDecimal.format(valorD1+valorD9+valorFrete)).replace('.', ','));

    }//GEN-LAST:event_btnLimparFormBDIActionPerformed

    private void btnPesquisarBDIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarBDIActionPerformed
        // TODO add your handling code here:
        consultaBDI();
    }//GEN-LAST:event_btnPesquisarBDIActionPerformed

    private void btnAlterarBDIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarBDIActionPerformed
        // TODO add your handling code here:
        //Verifica se o BDI já está cadastrado, pois precisa existir para ser alterado
        if(checaCodBDI() == true){ //Se for verdadeiro, existe e pode ser alterado
            if(validaCampos() == true){ //Verifica se os campos estão todos devidamente preenchidos
                //As linhas abaixo alteram os dados dos registros de Talões e fretes
                alterarTaloesD1();
                alterarTaloesD9();
                alterarFretes();
            }
            else{ //Mensagem de erro caso os campos não estejam devidamente preenchidos
                JOptionPane.showMessageDialog(null, "Verifique se todos os campos estão preenchidos corretamente!");
            }
        }
    }//GEN-LAST:event_btnAlterarBDIActionPerformed

    private void btnDeletarBDIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarBDIActionPerformed
        // TODO add your handling code here:
        deletarBDI();
        //deletar();
    }//GEN-LAST:event_btnDeletarBDIActionPerformed

    private void btnSalvarBDIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarBDIActionPerformed
        // TODO add your handling code here:
        codigosBuscados.clear();
        if(checaCodBDI() == false){ //Se não existir BDI cadastrado com o número informado
            if(validaCampos() == true){ //Se os campos estiverem devidamente preenchidos
                //A linha abaixo insere um BDI no sistema
                cadastraTalaoD1();
                cadastraTalaoD9();
                cadastraFrete();
                cadastraBDI();
            }
            else{ //Mensagem de erro caso os campos não estejam devidamente preenchidos
                JOptionPane.showMessageDialog(null, "Erro! Verifique se todos os campos estão devidamente preenchidos!");
            }
        }
        else{ //Mensagem de erro caso já exista um BDI com este número cadastrado
            JOptionPane.showMessageDialog(null, "Erro! BDI Nº " + txtNumBDI.getText() + " já está cadastrado!");
        }
    }//GEN-LAST:event_btnSalvarBDIActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterarBDI;
    private javax.swing.JButton btnDeletarBDI;
    private javax.swing.JButton btnLimparFormBDI;
    private javax.swing.JButton btnPesquisarBDI;
    private javax.swing.JButton btnSalvarBDI;
    private com.toedter.calendar.JCalendar calendarioBDI;
    public static javax.swing.JComboBox<String> cboCobradorBDI;
    private javax.swing.JComboBox<String> cboHorarioBDI;
    public static javax.swing.JComboBox<String> cboMotoristaBDI;
    public static javax.swing.JComboBox<String> cboPrefixoBDI;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblTotalBDI;
    private javax.swing.JTextField txtEncerranteD1;
    private javax.swing.JTextField txtEncerranteD9;
    private javax.swing.JTextField txtEncerranteFrete;
    private javax.swing.JTextField txtInicianteD1;
    private javax.swing.JTextField txtInicianteD9;
    private javax.swing.JTextField txtInicianteFrete;
    private javax.swing.JTextField txtItinerarioBDI;
    private javax.swing.JTextField txtNomeCobradorBDI;
    private javax.swing.JTextField txtNomeMotoristaBDI;
    private javax.swing.JTextField txtNumBDI;
    private javax.swing.JTextField txtValorD1;
    private javax.swing.JTextField txtValorD9;
    private javax.swing.JTextField txtValorFrete;
    // End of variables declaration//GEN-END:variables
}
