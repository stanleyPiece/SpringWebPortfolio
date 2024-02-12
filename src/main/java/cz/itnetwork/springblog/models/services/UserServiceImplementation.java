package cz.itnetwork.springblog.models.services;

import cz.itnetwork.springblog.database.entities.UserEntity;
import cz.itnetwork.springblog.database.repositories.UserRepository;
import cz.itnetwork.springblog.models.dto.UserDTO;
import cz.itnetwork.springblog.models.dto.mappers.UserMapper;
import cz.itnetwork.springblog.models.exceptions.DuplicateEmailException;
import cz.itnetwork.springblog.models.exceptions.PasswordsDoNotEqualException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    //metoda pro vytvoření uživatele
    @Override
    public void create(UserDTO user, boolean isAdmin) {
        if (!user.getPassword().equals(user.getConfirmPassword())) //kontrola shody hesel
            throw new PasswordsDoNotEqualException(); //vyvolání výjimky, pokud se neshodují

        UserEntity newUser = userMapper.toEntity(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword())); //ruční zahašování hesla, bez pomoci Mapperu/knihovny MapStruct

        /* způsob namapování atributů bez využití mapovače

        UserEntity newUser = new UserEntity();

        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword())); //zahašování hesla
        newUser.setAdmin(isAdmin);

         */

        try {
            userRepository.save(newUser); //uložení uživatele
        } catch (DataIntegrityViolationException exception) { //pokud uživatel používá již obsazenou e-mailovou adresu, vyvolá se výjimka
            throw new DuplicateEmailException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //loadUserByUserName - metoda pocházející z rozhraní UserDetailsService
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Uživatel " + username + " nebyl nalezen"));
    }
}
