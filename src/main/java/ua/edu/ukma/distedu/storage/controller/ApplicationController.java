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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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
        if (model.getAttribute("group") == null) {
            model.addAttribute("group", new Group());
        }
        return "group-add";
    }

    @PostMapping("/request-add-group")
    public String requestAddGroup(@ModelAttribute Group group, Model model) {
        Response<Group> groupResponse = groupService.save(group);
        if (!groupResponse.isOkay()) {
            model.addAttribute("errors", groupResponse.getErrorMessage());
            return addGroup(model);
        }
        return "redirect:/groups";
    }

    @GetMapping("/products-find")
    public String productsByGroup(@ModelAttribute("groupId") Long groupId,
                                  @ModelAttribute("findProductId") Long findProductId,
                                  @ModelAttribute("findProductName") String findProductName,
                                  Model model) {
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("productAmountChange", 0);
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
        if (model.getAttribute("product") == null) {
            model.addAttribute("product", new Product());
            model.addAttribute("groupId", 0);
        }
        model.addAttribute("groups", groupService.findAll());
        return "product-add";
    }

    @PostMapping("/request-add-product")
    public String requestAddProduct(@ModelAttribute Product product, @ModelAttribute("groupId") Long groupId, Model model) {
        if (groupId == 0){
            model.addAttribute("errors", new LinkedList<>(Collections.singleton("Select group")));
            model.addAttribute("product", product);
            return addProduct(model);
        }
        product.setGroup(groupService.findGroupById(groupId));
        Response<Product> productResponse = productService.save(product);
        if (!productResponse.isOkay()) {
            model.addAttribute("errors", productResponse.getErrorMessage());
            model.addAttribute("product", product);
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
        Response<Group> groupResponse = groupService.update(group);
        if (!groupResponse.isOkay()) {
            model.addAttribute("errors", groupResponse.getErrorMessage());
            return editGroup(group.getId(), model);
        }
        return "redirect:/groups";
    }

    @GetMapping("/edit-product")
    public String editProduct(@ModelAttribute("productID") long id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("groupId", product.getGroup().getId());
        model.addAttribute("productAmountChange", new Long(0));
        return "product-edit";
    }


    @PostMapping("/request-edit-product")
    public String requestEditProduct(@ModelAttribute Product product,
                                     @ModelAttribute("groupId") Long groupId,
                                     @ModelAttribute("productAmountChange") Long productAmountChange,
                                     Model model) {
        synchronized (productService){
            Product upToDate = productService.findProductById(product.getId());
//            if (!upToDate.equals(product)){
//                List<String> err = new ArrayList<>();
//                err.add("Product wasn't up-to-date. Try now.");
//                model.addAttribute("errors", err);
//                return editProduct(product.getId(), model);
//            }
            product.setGroup(groupService.findGroupById(groupId));
            upToDate.changeAmount(productAmountChange);
            product.setAmount(upToDate.getAmount());

            Response<Product> productResponse = productService.update(product);
            if (!productResponse.isOkay()) {
                model.addAttribute("errors", productResponse.getErrorMessage());
                return editProduct(product.getId(), model);
            }
        }
        return "redirect:/products";
    }


    @PostMapping("/request-delete-product")
    public String requestDeleteProduct(@ModelAttribute("productID") long id, Model model) {
        productService.delete(productService.findProductById(id));
        return "redirect:/products";
    }

}