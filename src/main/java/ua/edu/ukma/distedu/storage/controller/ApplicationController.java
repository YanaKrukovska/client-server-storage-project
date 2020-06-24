package ua.edu.ukma.distedu.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.edu.ukma.distedu.storage.persistence.model.Group;
import ua.edu.ukma.distedu.storage.persistence.model.Product;
import ua.edu.ukma.distedu.storage.service.GroupService;
import ua.edu.ukma.distedu.storage.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

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
        return "login";
    }

    //todo
    @PostMapping("/error")
    public String error(Model model) {
        return "login";
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

    @GetMapping("/products-find")
    public String productsByGroup(@ModelAttribute("groupId") Long groupId,
                                  @ModelAttribute("findProductId") Long findProductId,
                                  @ModelAttribute("findProductName") String findProductName,
                                  Model model) {
        model.addAttribute("groups", groupService.findAll());
        //To display previously selected
        model.addAttribute("groupId", groupId);
        model.addAttribute("findProductId",findProductId);
        model.addAttribute("findProductName",findProductName);
        List<Product> productListGroup;
        if (groupId == 0) {
            productListGroup = productService.findAll();
        } else {
            productListGroup =  productService.findAllByGroup(groupService.findGroupById(groupId));
        }
        List<Product> byName = productService.findByName(findProductName);
        List<Product> productList = productListGroup.stream()
                .distinct()
                .filter(byName::contains)
                .collect(Collectors.toList());
        if (findProductId!=0){
            Product byID = productService.findProductById(findProductId);
            if (productList.contains(byID)) {
                productList.clear();
                productList.add(byID);
            } else {
                productList.clear();
            }
        }
        model.addAttribute("products",productList);
        long value = 0;
        for (Product p: productList) {
            value+= p.getAmount()*p.getPrice();
        }
        model.addAttribute("value",value);
        return "products";
    }

    @GetMapping("/products")
    public String products(Model model) {
        return productsByGroup(0L, 0L,"", model);
    }

    @GetMapping("/add-product")
    public String addProduct(Model model) {
        if (model.getAttribute("product") == null) model.addAttribute("product", new Product());
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("groupId", 0);
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
        productService.save(product);
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