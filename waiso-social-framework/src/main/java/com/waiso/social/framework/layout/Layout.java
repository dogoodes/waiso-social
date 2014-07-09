package com.waiso.social.framework.layout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import com.waiso.social.framework.exceptions.CriticalUserException;
import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.utilitario.StringUtils;

/**
 * Classe responsavel por gerar uma String conforme layout informaco
 * 
 *	   Layout layout = Layout.newInstance("LayoutExample.txt");
 *     layout.addParameter("CD_SRV", extratoLiberacao.getTipoExtrato().getCodigoServico());
 *     layout.addParameter("CD_PGM", "AFCINTER");
 *     layout.addParameter("SG_SIS_ORI", "AFC");
 *     layout.addParameter("TP_ACC", "00");
 *     layout.addParameter("TP_CLA", "A");
 *     layout.addParameter("QT_PRC", "00100");
 *     layout.addParameter("CD_TPO_OPC", extratoLiberacao.getOpcao().getOpcao());
 *     layout.addParameter("CD_EMP_USU", "00002");
 *     layout.addParameter("CD_TRAN", "E403");
 *     layout.addParameter("CD_AGE_ABN_USU", userDetails.getIntermediario().getCodigoAgencia());
 *     layout.addParameter("CD_PAT_CRR", userDetails.getIntermediario().getCodigoTAB());
 *     layout.addParameter("CD_PAT_CRR2", userDetails.getIntermediario().getCodigoTAB());
 *     layout.addParameter("NO_APV_PPT_CRE", extratoLiberacao.getNumeroAprovacaoPptIni());
 *     layout.addParameter("NO_APV_PPT_CRE2", extratoLiberacao.getNumeroAprovacaoPptIni());
 *     layout.addParameter("DT_LIB_FIN", AymoreUtil.dateToString(extratoLiberacao.getDataLiberacao(), "dd.MM.yyyy"));
 *     layout.addParameter("DT_EFET", AymoreUtil.dateToString(extratoLiberacao.getDataEfetivacao(), "dd.MM.yyyy"));
 *     if (intermediarioLogado.equals(IntermediarioEnum.HP)){
 *         layout.addParameter("CD_SGM", "367");
 *     }
 *     layout.addParameter("TS_INC", extratoLiberacao.getTsIncIni());
 *     String envio = layout.getEnvio();
 *     
 *     
 *     ------------------- LAYOUT FILE EXAMPLE ---------
 *     
 *     CD_SRV*CHAR*8
 *     CD_PGM*CHAR*8
 *     SG_SIS_ORI*CHAR*3
 *     TP_ACC*CHAR*2
 *     TP_CLA*CHAR*1
 *     CD_USU*CHAR*20
 *     QT_PRC*CHAR*5
 *     CD_TPO_OPC*CHAR*1
 *     CD_EMP_USU*CHAR*5
 *     CD_AGE_ABN_USU*PIC*4
 *     CD_IMP_USU*PIC*4
 *     CD_TRAN*CHAR*4
 *     FILLER2*CHAR*35
 *     CD_IND_CONT*CHAR*1
 *     QT_LID*PIC*5
 *     CD_RET_COD*PIC*1
 *     CD_SQL_COD*PIC*3
 *     DS_MSG*CHAR*70
 *     FILLER3*CHAR*20
 *     CD_PAT_CRR*PIC*11
 *     DT_LIB_FIN*CHAR*10
 *     CD_STA_PPT_CRE*CHAR*2
 *     NO_APV_PPT_CRE*PIC*11
 *     CD_PAT_CRR2*PIC*11
 *     DT_EFET*CHAR*10
 *     CD_STA_PPT_CRE2*CHAR*2
 *     NO_APV_PPT_CRE2*PIC*11
 *     TS_INC*CHAR*26
 *     CD_SGM*PIC*3
 *     FILLER4*CHAR*13
 */


public class Layout {

    private Map<String, InfoLayout> buffer = new LinkedHashMap<String, InfoLayout>();

    private Layout() {
    }

    public static Layout newInstance(File layoutFile) throws Exception {
        Layout layout = new Layout();
        layout.load(layoutFile);
        return layout;
    }
    
    public static Layout newInstance(InputStream layoutFile) throws Exception {
        Layout layout = new Layout();
        layout.load(layoutFile);
        return layout;
    }
    
    protected void load(File f) throws Exception{
    	BufferedReader br = new BufferedReader(new FileReader(f));
        String info = null;
        while ((info = br.readLine()) != null) {
            String[] infos = info.split("[*]");
            InfoLayout infoLayout = new InfoLayout(infos[0], infos[1], Integer.parseInt(infos[2].trim()));
            buffer.put(infos[0], infoLayout);
        }
    }
    
    
    protected void load(InputStream f) throws Exception{
    	BufferedReader br = new BufferedReader(new InputStreamReader(f));
        String info = null;
        while ((info = br.readLine()) != null) {
            String[] infos = info.split("[*]");
            InfoLayout infoLayout = new InfoLayout(infos[0], infos[1], Integer.parseInt(infos[2].trim()));
            buffer.put(infos[0], infoLayout);
        }
    }

    public void addParameter(String nome, Object valor) {
        InfoLayout infoLayout = buffer.get(nome);
        infoLayout.setValor(valor);
    }

    public String getEnvio() {
        StringBuilder envio = new StringBuilder();
        for (Map.Entry<String, InfoLayout> entry : buffer.entrySet()) {
            String valor = (String) entry.getValue().getValor();
            envio.append(valor);
        }
        return envio.toString();
    }

    private class InfoLayout {

        private final String nome;

        private final String tipo;

        private final int tamanho;

        private Object valor;

        public InfoLayout(String nome, String tipo, int tamanho) {
            this.nome = nome;
            this.tipo = tipo;
            this.tamanho = tamanho;
        }

        public void setValor(Object valor) {
            this.valor = valor;
        }

        public Object getValor() {
            if ("CHAR".equals(tipo)) {
                valor = (valor == null) ? "" : valor;
                String newValor = valor.toString();
                if (newValor.length() > tamanho){
                	newValor = newValor.substring(0,tamanho);
                }else{
                	newValor  = StringUtils.rightPad(valor.toString(), tamanho);
                }
                return newValor;
            } else if ("PIC".equals(tipo)) {
                String valorCru = "";
                valor = (valor == null) ? "0" : valor;
                if (valor instanceof Number) {
                    Number n = (Number) valor;
                    valorCru = n.toString();
                    valorCru = valorCru.replaceAll("[.,]", "");
                } else {
                    valorCru = String.valueOf(valor);
                }
                return StringUtils.leftPad(valorCru, "0", tamanho);
            }
            String message = GerenciadorMensagem.getMessage("error.layout.type.not.expected", tipo);
            CriticalUserException exception = new CriticalUserException(Layout.class, message);
            throw exception;
        }

    }
}
