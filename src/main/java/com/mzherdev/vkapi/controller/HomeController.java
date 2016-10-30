package com.mzherdev.vkapi.controller;

import com.mzherdev.vkapi.model.Group;
import com.mzherdev.vkapi.model.Post;
import com.mzherdev.vkapi.model.User;
import com.mzherdev.vkapi.service.GroupService;
import com.mzherdev.vkapi.service.PostService;
import com.mzherdev.vkapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.mzherdev.vkapi.VkApiClient.*;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @Autowired
    PostService postService;

    String accessToken = "";

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.addAttribute("head", "This is a simple app to view groups of vk.com user");
        model.addAttribute("link", AUTH_URL);
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String status(@RequestParam(value = "code", required = false) String code,
                         ModelMap model) {
        requestVkApi(code);

        model.addAttribute("user", userService.getAll());
        model.addAttribute("groups", groupService.getAll());
        return "status";
    }

    @RequestMapping(value = "/addGroup", method = RequestMethod.GET)
    public String showAddGroupForm(Model model) {
        Group group = new Group(null, "", false, "", "");
        model.addAttribute("groupForm", group);
        return "form";

    }

    @RequestMapping(value = "/saveGroup", method = RequestMethod.POST)
    public String saveGroup(
            @ModelAttribute("groupForm") @Validated Group group,
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "form";
        } else {
            String query = group.getLink().substring(group.getLink().lastIndexOf("/") + 1);
            Group newGroup = addGroup(query, accessToken);
            if (group.getName() != null && !group.getName().isEmpty())
                newGroup.setName(group.getName());
            groupService.save(newGroup);
            List<Post> posts = getPosts(newGroup.getId(), accessToken);
            for (Post post : posts) {
                postService.save(post, newGroup.getId());
            }
            return "redirect:/login";

        }
    }

    private void requestVkApi(String code) {
        if (code != null && !code.isEmpty())
            accessToken = getAccessToken(code);

        User user = getAccountInfo(accessToken);
        userService.save(user);

//          Add all groups where user is a member, not needed
//        List<Group> groups = getGroups(accessToken);
//        for (Group group : groups) {
//            groupService.save(group);
//
//            List<Post> posts = getPosts(group.getId(), accessToken);
//            for(Post post : posts) {
//                postService.save(post, group.getId());
//            }
//        }

    }

}
