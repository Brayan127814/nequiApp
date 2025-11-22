package com.nequiApp.nequiApp.Services;

import com.nequiApp.nequiApp.Entities.CuentaEntity;
import com.nequiApp.nequiApp.Entities.RolEntity;
import com.nequiApp.nequiApp.Entities.UsuarioEntity;
import com.nequiApp.nequiApp.Repositorys.CuentaRepository;
import com.nequiApp.nequiApp.Repositorys.RolRepository;
import com.nequiApp.nequiApp.Repositorys.UsuarioRepository;
import com.nequiApp.nequiApp.dtos.RolRequest;
import com.nequiApp.nequiApp.dtos.UsuarioRequest;
import com.nequiApp.nequiApp.dtos.UsuarioResponse;
import com.nequiApp.nequiApp.utils.AccountNumberGenerator;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuariosService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final CuentaRepository cuentaRepository;

    public UsuariosService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, RolRepository rolRepository, AccountNumberGenerator accountNumberGenerator, CuentaRepository cuentaRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.accountNumberGenerator = accountNumberGenerator;
        this.cuentaRepository = cuentaRepository;
    }


    public UsuarioResponse register(UsuarioRequest data) {

        //Verficar si el correo ya existe
        if (usuarioRepository.findByEmail(data.getEmail()) != null) {
            throw new RuntimeException("Cuenta de correo ya se encuetra registrado");
        }

        //Verififar si el correo
        if (usuarioRepository.findByPhone(data.getPhone()) != null) {
            throw new RuntimeException("Número de telefono ya registrado");
        }

        //ASIGNAR ROLES
        RolEntity roles = rolRepository.findByRoleName("ROLE_USER");
        if (roles == null) {
            throw new RuntimeException("Rol ROLE_USER no existe");
        }

        //Crar usuario
        UsuarioEntity nuevoUsuario = new UsuarioEntity();
        nuevoUsuario.setFullName(data.getFullName());
        nuevoUsuario.setEmail(data.getEmail());
        nuevoUsuario.setPhone(data.getPhone());
        nuevoUsuario.setPassword(passwordEncoder.encode(data.getPassword()));
        nuevoUsuario.getRoles().add(roles);


        //Crear cuenta asociada

        String accountNumber;
        do {

            accountNumber = accountNumberGenerator.generateAccountNumber();


        } while (cuentaRepository.existsByAccountNumber(accountNumber));

        CuentaEntity cuenta = new CuentaEntity();

        cuenta.setAccountNumber(accountNumber);
        cuenta.setBalance(0.0);
        cuenta.setStado("ACTIVE");
        cuenta.setUsuario(nuevoUsuario);

        nuevoUsuario.getCuentas().add(cuenta);

        UsuarioEntity usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        List<String> StringRoles = usuarioGuardado.getRoles().stream().map(RolEntity::getRoleName).toList();

        return new UsuarioResponse(

                usuarioGuardado.getId(),
                usuarioGuardado.getFullName(),
                usuarioGuardado.getEmail(),
                usuarioGuardado.getPhone(),
                accountNumber,
                StringRoles
        );
    }


}

/**
 *
 * //Registrar usuario
 * public UsuarioResponse register(UsuarioRequest data) {
 * <p>
 * UsuarioEntity usuario = usuarioRepository.findByEmail(data.getEmail());
 * <p>
 * if (usuario != null) {
 * throw new RuntimeException("Email ya registrado");
 * }
 * <p>
 * UsuarioEntity newUsuario = new UsuarioEntity();
 * newUsuario.setName(data.getName());
 * newUsuario.setUsername(data.getUsername());
 * newUsuario.setEmail(data.getEmail());
 * newUsuario.setPassword(passwordEncoder.encode(data.getPassword()));
 * <p>
 * Set<RolEntity> roles = new HashSet<>();
 * <p>
 * for (String roleName : data.getRoles()) {
 * RolEntity rol = rolRepository.findByRoleName(roleName);
 * <p>
 * <p>
 * if (rol == null) {
 * throw new RuntimeException("El rol " + roleName + " no existe ");
 * }
 * <p>
 * // Agregar rol al usuario
 * roles.add(rol);
 * <p>
 * // 🔥 Agregar usuario al rol (lado inverso)
 * rol.getUsuarios().add(newUsuario);
 * }
 * <p>
 * newUsuario.setRoles(roles);
 * <p>
 * UsuarioEntity usuarioGuardado = usuarioRepository.save(newUsuario);
 * <p>
 * List<String> rolesString = usuarioGuardado.getRoles()
 * .stream()
 * .map(RolEntity::getRoleName)
 * .toList();
 * <p>
 * return new UsuarioResponse(
 * usuarioGuardado.getName(),
 * usuarioGuardado.getUsername(),
 * usuarioGuardado.getEmail(),
 * rolesString
 * );
 * }
 */