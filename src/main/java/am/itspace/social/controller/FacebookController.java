package am.itspace.social.controller;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class FacebookController {

	private Facebook facebook;

	private ConnectionRepository connectionRepository;



	public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
		this.facebook = facebook;
		this.connectionRepository = connectionRepository;
	}



	@GetMapping
	public String getfacebookFeeds(Model model) {
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return "redirect:/connect/facebook";
		}
		PagedList<Post> posts = facebook.feedOperations().getPosts();
		PagedList<User> friendProfiles = facebook.friendOperations().getFriendProfiles();
		model.addAttribute("profileName", posts.get(0).getFrom().getName());
		model.addAttribute("posts", posts);
		model.addAttribute("friends",friendProfiles);
		return "profile";
	}

	@GetMapping("/friends")
	public String getUserFriends(Model model){
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null){
			return "redirect:/connect/facebook";
		}
		PagedList<Invitation> friendProfiles = facebook.eventOperations().getAttending();
		model.addAttribute("profileName",facebook.feedOperations().getPosts().get(0).getFrom().getName());
		model.addAttribute("friends",friendProfiles);
		return "friends";
	}
}
