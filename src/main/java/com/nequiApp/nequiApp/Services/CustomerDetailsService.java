package com.nequiApp.nequiApp.Services;

import com.nequiApp.nequiApp.Entities.UsuarioEntity;
import com.nequiApp.nequiApp.Repositorys.UsuarioRepository;
import org.hibernate.sql.ast.tree.from.CorrelatedTableGroup;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public  class CustomerDetailsService implements  UserDetailsService {


    private  final UsuarioRepository usuarioRepository;

    public CustomerDetailsService (UsuarioRepository usuarioRepository){
         this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UsuarioEntity usuario = usuarioRepository.findByEmail(email);
         if(usuario == null){
              throw  new UsernameNotFoundException("Usuario no registrado");
         }

       //  var authorities = usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority("ROLE_"+ rol.getRoleName())).toList();
        var authorities = usuario.getRoles().stream().map( r -> new SimpleGrantedAuthority(r.getRoleName())).toList();
        System.out.println(authorities);
        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(authorities)
                .build();
    }
}
