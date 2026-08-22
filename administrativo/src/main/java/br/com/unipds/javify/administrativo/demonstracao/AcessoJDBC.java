package br.com.unipds.javify.administrativo.demonstracao;

import br.com.unipds.javify.administrativo.domain.Endereco;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class AcessoJDBC {
    @Value("${spring.datasource.url}") String url;
    @Value("${spring.datasource.username}") String user;
    @Value("${spring.datasource.password}") String pass;


    public void executar() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);


            ps = conn.prepareStatement("SELECT * FROM endereco WHERE codigo_postal = ?");
            ps.setString(1, "01508000");
            rs = ps.executeQuery();


            Endereco e = null;
            if (rs.next()) {
                e = new Endereco();
                e.setCodigoPostal(rs.getString("codigo_postal"));
                e.setLogradouro(rs.getString("logradouro"));
                e.setBairro(rs.getString("bairro"));
                System.out.printf("Endereço com JDBC: %s \n ", e);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
