package uz.nammqi.workload.v1.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.amplicode.rautils.patch.ObjectPatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uz.nammqi.workload.v1.entity.Users;
import uz.nammqi.workload.v1.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/users")
public class UsersResource {

    private final UsersRepository usersRepository;

    private final ObjectPatcher objectPatcher;

    public UsersResource(UsersRepository usersRepository,
                         ObjectPatcher objectPatcher) {
        this.usersRepository = usersRepository;
        this.objectPatcher = objectPatcher;
    }

    @GetMapping
    public Page<Users> getList(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Users getOne(@PathVariable Long id) {
        Optional<Users> usersOptional = usersRepository.findById(id);
        return usersOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    @GetMapping("/by-ids")
    public List<Users> getMany(@RequestParam List<Long> ids) {
        return usersRepository.findAllById(ids);
    }

    @PostMapping
    public Users create(@RequestBody Users users) {
        return usersRepository.save(users);
    }

    @PatchMapping("/{id}")
    public Users patch(@PathVariable Long id, @RequestBody JsonNode patchNode) {
        Users users = usersRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        users = objectPatcher.patch(users, patchNode);

        return usersRepository.save(users);
    }

    @PatchMapping
    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) {
        List<Users> users = new ArrayList<>(usersRepository.findAllById(ids));

        users.replaceAll(user -> objectPatcher.patch(user, patchNode));

        List<Users> resultUsers = usersRepository.saveAll(users);
        return Users.getId(resultUsers);
    }

    @DeleteMapping("/{id}")
    public Users delete(@PathVariable Long id) {
        Users users = usersRepository.findById(id).orElse(null);
        if (users != null) {
            usersRepository.delete(users);
        }
        return users;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        usersRepository.deleteAllById(ids);
    }
}
