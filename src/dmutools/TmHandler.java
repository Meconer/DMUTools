/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dmutools;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;

/**
 *
 * @author mats
 */
class TmHandler extends TransferHandler {

    public TmHandler() {
    }
    @Override
    public boolean canImport(TransferHandler.TransferSupport info) {
        return info.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }
    
    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY_OR_MOVE;
    }
    
    @Override
    public boolean importData(TransferHandler.TransferSupport info) {
        if ( !info.isDrop() ) {
            return false;
        }
        
        Transferable dataToTransfer = info.getTransferable();
        List<File> fileList = null;
        try {
            fileList = (List) dataToTransfer.getTransferData(DataFlavor.javaFileListFlavor);
            System.out.println(fileList.size());
            System.out.println(fileList.get(0));
        } catch (UnsupportedFlavorException | IOException ex) {
            Logger.getLogger(DmuTransferHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        if ( fileList != null ) {
            if (fileList.size() > 0 ) readFile(  fileList.get(0), (JTextArea) info.getComponent());
        }
        return true;
    }

    private void readFile(File fileToRead, JTextArea component) {
        try {
            try (BufferedReader reader = Files.newBufferedReader( fileToRead.toPath() , Charset.defaultCharset())) {
                String line;
                while (( line = reader.readLine()) != null ) {
                    component.append(line + "\n");
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
