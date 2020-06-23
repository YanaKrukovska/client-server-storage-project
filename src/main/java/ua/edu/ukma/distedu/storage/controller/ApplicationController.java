package ua.edu.ukma.distedu.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.edu.ukma.distedu.storage.persistence.model.Group;
import ua.edu.ukma.distedu.storage.persistence.model.Product;
import ua.edu.ukma.distedu.storage.persistence.model.Response;
import ua.edu.ukma.distedu.storage.service.GroupService;
import ua.edu.ukma.distedu.storage.service.ProductService;

@Controller
public class ApplicationController {

    private final ProductService productService;
    private final GroupService groupService;

    @Autowired
    public ApplicationController(ProductService productService, GroupService groupService) {
        this.productService = productService;
        this.groupService = groupService;
    }

    @GetMapping("/")
    public String editProduct(Model model) {
        return "index";
    }

    //todo
    @PostMapping("/error")
    public String error(Model model) {
        return "index";
    }


    @GetMapping("/groups")
    public String groups(Model model) {
        model.addAttribute("groups", groupService.findAll());
        return "groups";
    }

    @PostMapping("/request-delete-group")
    public String requestDeleteGroup(@ModelAttribute("groupID") long id, Model model) {
        groupService.delete(groupService.findGroupById(id));
        return "redirect:/groups";
    }

    @GetMapping("/add-group")
    public String addGroup(Model model) {
        if (model.getAttribute("group") == null) model.addAttribute("group", new Group());
        return "group-add";
    }

    @PostMapping("/request-add-group")
    public String requestAddGroup(@ModelAttribute Group group, Model model) {
        String error = isValidGroup(group);
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("group", group);
            return addGroup(model);
        }
        groupService.save(group);
        return "redirect:/groups";
    }

    @GetMapping("/products-by-group")
    public String productsByGroup(@ModelAttribute("groupId") Long groupId, Model model) {
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("groupId", groupId);
        if (groupId == 0) {
            model.addAttribute("products", productService.findAll());
        } else {
            model.addAttribute("products", productService.findAllByGroup(groupService.findGroupById(groupId)));
        }
        return "products";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("groupId", 0);
        return productsByGroup(0L, model);
    }

    @GetMapping("/add-product")
    public String addProduct(Model model) {
        if (model.getAttribute("product") == null){
            model.addAttribute("product", new Product());
            model.addAttribute("groupId", 0);
        }
        model.addAttribute("groups", groupService.findAll());
        return "product-add";
    }

    @PostMapping("/request-add-product")
    public String requestAddProduct(@ModelAttribute Product product, @ModelAttribute("groupId") Long groupId, Model model) {
        String error = isValidProduct(product);
        if (groupId == 0) {
            error = "Select group";
        }
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("product", product);
            return addProduct(model);
        }
        product.setGroup(groupService.findGroupById(groupId));

        Response<Product> responseProduct = productService.save(product);
        if (!responseProduct.isOkay()) {
            model.addAttribute("error", responseProduct.getErrorMessage());
            model.addAttribute("product", responseProduct.getObject());
            model.addAttribute("groupId", responseProduct.getObject().getGroup().getId());
            return addProduct(model);
        }
        return "redirect:/products";
    }

    @GetMapping("/edit-group")
    public String editGroup(@ModelAttribute("groupID") long id, Model model) {
        model.addAttribute("group", groupService.findGroupById(id));
        return "group-edit";
    }


    @PostMapping("/request-edit-group")
    public String requestEditGroup(@ModelAttribute Group group, Model model) {
        String error = isValidGroup(group);
        if (error != null) {
            model.addAttribute("error", error);
            return editGroup(group.getId(), model);
        }
        groupService.update(group);
        return editGroup(group.getId(), model);
    }

    @GetMapping("/edit-product")
    public String editProduct(@ModelAttribute("productID") long id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("groupId", product.getGroup().getId());
        return "product-edit";
    }


    @PostMapping("/request-edit-product")
    public String requestEditProduct(@ModelAttribute Product product, @ModelAttribute("groupId") Long groupId, Model model) {
        String error = isValidProduct(product);
        if (error != null) {
            model.addAttribute("error", error);
            return editProduct(product.getId(), model);
        }
        product.setGroup(groupService.findGroupById(groupId));
        productService.update(product);
        return editProduct(product.getId(), model);
    }


    @PostMapping("/request-delete-product")
    public String requestDeleteProduct(@ModelAttribute("productID") long id, Model model) {
        productService.delete(productService.findProductById(id));
        return "redirect:/products";
    }

    private static String isValidProduct(Product product) {
        String error = null;
        if (product.getAmount() < 0) {
            error = "Amount cannot be < 0";
        }
        if (product.getPrice() < 0) {
            error = "Price cannot be < 0";
        }
        if (product.getName().equals("")) {
            error = "Name cannot be empty";
        }
        return error;
    }

    private static String isValidGroup(Group group) {
        String error = null;
        if (group.getName().equals("")) {
            error = "Name cannot be empty";
        }
        if (group.getDescription().equals("")) {
            error = "Description cannot be empty";
        }
        return error;
    }
}