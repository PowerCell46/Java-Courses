//package bg.softuni.mvcworkshop.services.impls;
//
//import bg.softuni.mvcworkshop.entities.User;
//import bg.softuni.mvcworkshop.repositories.UserRepository;
//import bg.softuni.mvcworkshop.services.interfaces.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Fetch the user from the database
//        User user = this.userRepository.findByUsername(username);
//
//        // If user is not found, throw an exception
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        // Convert your User entity to a UserDetails object
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword()) // Ensure password is encoded
//                .authorities(user.getAuthorities()) // Convert roles/authorities as needed
//                .accountLocked(!user.isAccountNonLocked())
//                .disabled(!user.isEnabled())
//                .build();
//    }
//}
