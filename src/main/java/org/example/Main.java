package org.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;
import  java.awt.*;
import  java.awt.event.ActionEvent;
import  java.awt.event.ActionListener;

import static java.lang.System.exit;

public class Main
{
    public static void main(String[] args)
    {
        String path = "http://localhost:8080/nome/";

        JFrame frame = new JFrame("Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JTextField inputField1 = new JTextField();
        JTextField inputField2 = new JTextField();
        JTextField outputField = new JTextField();

        inputField1.setFont((new Font("Arial", Font.PLAIN, 30)));
        inputField2.setFont((new Font("Arial", Font.PLAIN, 30)));

        JLabel label1 = new JLabel("Altura:");
        label1.setFont((new Font("Arial", Font.BOLD, 30)));
        JLabel label2 = new JLabel("Massa:");
        label2.setFont((new Font("Arial", Font.BOLD, 30)));


        panel.add(label1);
        panel.add(inputField1);
        panel.add(label2);
        panel.add(inputField2);

        String [] buttonLabels = {
                "Enviar","Sair"
        };

        for(String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            panel.add(button);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{

                        if(label.equals("Enviar"))
                        {
                            String content=inputField1.getText();
                            String url = "http://localhost:8080/nome/" + inputField1.getText();

                            String content2=inputField2.getText();

                            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

                            connection.setRequestMethod("GET");
                            int responseCode = connection.getResponseCode();
                            System.out.println("Código de Resposta: " + responseCode);
                            BufferedReader reader = new BufferedReader(new
                                    InputStreamReader(connection.getInputStream()));
                            String linha;
                            StringBuilder resposta = new StringBuilder();
                            while ((linha = reader.readLine()) != null) {
                                resposta.append(linha);
                            }
                            reader.close();
                            System.out.println("Resposta: " + resposta.toString());
                            inputField2.setText(resposta.toString());
                            connection.disconnect();
                        }

                        if(label.equals("Sair"))
                        {
                            System.exit(0);
                        }


                    }
                    catch (MalformedURLException ex)
                    {
                        ex.printStackTrace();
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                    catch(NumberFormatException ex){
                        outputField.setText("Erro: Entrada invalida");
                    }
                }
            });
        }
        frame.add(panel);
        frame.setVisible(true);
    }
}