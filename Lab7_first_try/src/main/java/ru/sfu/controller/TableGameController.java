package ru.sfu.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sfu.config.JmsConfig;
import ru.sfu.model.Message;
import ru.sfu.model.TableGame;
import ru.sfu.repository.TableGameRepository;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/table_games")
public class TableGameController {
    private final TableGameRepository repo;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TableGameController(TableGameRepository repo, RabbitTemplate rabbitTemplate) {
        this.repo = repo;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping()
    public String menu(){
        return "table_games/menu";
    }

    @GetMapping("/{id}")
    public String showGame(@PathVariable("id") int id, Model model){
        Optional<TableGame> tg = repo.findById(id);
        if (tg.isPresent()) {
            model.addAttribute("game", tg.get());
            return "table_games/showGame";
        }

        return "redirect:/table_games";
    }

    @GetMapping("/show")
    public String showGames(Model model){
        model.addAttribute("games", repo.findAll());
        return "table_games/show";
    }

    @GetMapping("/add")
    public String newGame(Model model){
        model.addAttribute("game", new TableGame());
        return "table_games/add";
    }

    @PostMapping("/add")
    public String addGame(@ModelAttribute("game") @Valid TableGame game,
                          BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "table_games/add";
        }

        repo.save(game);
        rabbitTemplate.convertAndSend("tg-queue", new Message("Created: ", game));
        return "redirect:/table_games";
    }

    @PostMapping("/{id}/buy")
    public String buyGame(@PathVariable("id") int id, TableGame game){
        rabbitTemplate.convertAndSend("tg-queue", new Message("The buyer wants to buy: ", game));
        return "redirect:/table_games/show";
    }


    @GetMapping("/{id}/edit")
    public String editGame(@PathVariable("id") int id, Model model){
        Optional<TableGame> tg = repo.findById(id);
        if (tg.isPresent()){
            model.addAttribute("game", tg.get());
            return "table_games/edit";
        }


        return "redirect:/table_games";
    }

    @PostMapping("/{id}/edit")
    public String updateGame(@PathVariable("id") int id,
                             @ModelAttribute("game") @Valid TableGame game,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "table_games/edit";
        }
        rabbitTemplate.convertAndSend("tg-queue", new Message("Edited: ", game));
        repo.save(game);
        return "redirect:/table_games";
    }

    @PostMapping("delete/{id}")
    public String deleteGame(@PathVariable("id") int id){
        TableGame game;

        Optional<TableGame> tg = repo.findById(id);
        if (tg.isPresent()){
            game = tg.get();
            repo.deleteById(id);
            rabbitTemplate.convertAndSend("tg-queue", new Message("Deleted: ", game));
        }

        return "redirect:/table_games";
    }

    @GetMapping("search")
    public String findGame(@RequestParam("price") int maxPrice, Model model)
    {
        model.addAttribute("games", repo.findAllByPriceIsLessThanEqual(maxPrice));

        return "/table_games/show";
    }

    @GetMapping("find")
    public String showFoundedGames(){
        return "table_games/find";
    }
}
