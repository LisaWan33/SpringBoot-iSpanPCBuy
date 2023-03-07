package com.ispan.pcbuy.controller;

import com.ispan.pcbuy.constant.ProductCategory;
import com.ispan.pcbuy.dto.*;
import com.ispan.pcbuy.model.Product;
import com.ispan.pcbuy.service.ProductService;
import com.ispan.pcbuy.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Valid
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            // 查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            //  排序 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc")         String sort,

            //分頁 (要加@Valid註解才能使用@Min@Max)
            @RequestParam(defaultValue = "8") @Min(0) @Max(100) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0)           Integer offset
    ){
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        //取得 product list
        List<Product> productList = productService.getProducts(productQueryParams);

        //取得 product 總數
        Integer total = productService.countProduct(productQueryParams);

        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResult(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){

        Product product = productService.getProductById(productId);
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/products/{category}/List")
    public ResponseEntity<List<Product>> getProductsFromCategory(
            @PathVariable(required = true) String category){
        productService.getProductsFromCategory(category);
        List<Product> productList = productService.getProductsFromCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{category}/Filter")
    public ResponseEntity<List<Product>> getCategoryByFilter(
            @PathVariable(required = true) ProductCategory category,
            @RequestParam(required = false) String filterI,
            @RequestParam(required = false) String filterII,
            @RequestParam(required = false) String filterIII){
//        System.out.println("我是Category  = " + category.name());
//        System.out.println("我是FilterI   = " + filterI);
//        System.out.println("我是FilterII  = " + filterII);
//        System.out.println("我是FilterIII = " + filterIII);
        List<Product> productList = productService.getCategoryByFilter(category, filterI, filterII, filterIII);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @PostMapping("/productCreate")
    public  ResponseEntity<Product> createProduct(HttpServletRequest httpServletRequest){
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName(httpServletRequest.getParameter("productName"));
        productRequest.setCategory((ProductCategory.valueOf(httpServletRequest.getParameter("category"))));
        productRequest.setBrand(httpServletRequest.getParameter("brand"));
        productRequest.setSeries(httpServletRequest.getParameter("series"));

        if (httpServletRequest.getParameter("watt")!=null)
        {productRequest.setWatt(Integer.parseInt(httpServletRequest.getParameter("watt")));}

        productRequest.setSocket(httpServletRequest.getParameter("socket"));

        if (httpServletRequest.getParameter("score")!=null)
        {productRequest.setScore(Integer.parseInt(httpServletRequest.getParameter("score")));}

        productRequest.setSize(httpServletRequest.getParameter("size"));

        if(httpServletRequest.getParameter("coolerLength")!=null)
        {productRequest.setCoolerLength(Double.parseDouble(httpServletRequest.getParameter("coolerLength")));}

        if (httpServletRequest.getParameter("coolerHeight")!=null)
        {productRequest.setCoolerHeight(Double.parseDouble(httpServletRequest.getParameter("coolerHeight")));}

        if (httpServletRequest.getParameter("gpuLength")!=null)
        {productRequest.setGpuLength(Double.parseDouble(httpServletRequest.getParameter("gpuLength")));}

        if (httpServletRequest.getParameter("capacity")!=null)
        {productRequest.setCapacity(Integer.parseInt(httpServletRequest.getParameter("capacity")));}

        if (httpServletRequest.getParameter("state")!=null)
        {productRequest.setState(Boolean.parseBoolean(httpServletRequest.getParameter("state")));}

        productRequest.setDescription(httpServletRequest.getParameter("description"));
        productRequest.setImageUrl(httpServletRequest.getParameter("imageUrl"));

        if(httpServletRequest.getParameter("stock")!=null)
        {productRequest.setStock(Integer.parseInt(httpServletRequest.getParameter("stock")));}

        if (httpServletRequest.getParameter("price")!=null)
        {productRequest.setPrice(Integer.parseInt(httpServletRequest.getParameter("price")));}

        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        System.out.println(productRequest.toString());
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping("/productUpdate")
    public ResponseEntity<Product> updateProduct(HttpServletRequest httpServletRequest){
        Integer productId = Integer.parseInt(httpServletRequest.getParameter("productId"));
        Product product = productService.getProductById(productId);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName(httpServletRequest.getParameter("productName"));
        productRequest.setCategory((ProductCategory.valueOf(httpServletRequest.getParameter("category"))));
        productRequest.setBrand(httpServletRequest.getParameter("brand"));
        productRequest.setSeries(httpServletRequest.getParameter("series"));
        if (httpServletRequest.getParameter("watt")!=null)
        {productRequest.setWatt(Integer.parseInt(httpServletRequest.getParameter("watt")));}
        productRequest.setSocket(httpServletRequest.getParameter("socket"));
        if (httpServletRequest.getParameter("score")!=null)
        {productRequest.setScore(Integer.parseInt(httpServletRequest.getParameter("score")));}
        productRequest.setSize(httpServletRequest.getParameter("size"));
        if(httpServletRequest.getParameter("coolerLength")!=null)
        {productRequest.setCoolerLength(Double.parseDouble(httpServletRequest.getParameter("coolerLength")));}
        if (httpServletRequest.getParameter("coolerHeight")!=null)
        {productRequest.setCoolerHeight(Double.parseDouble(httpServletRequest.getParameter("coolerHeight")));}
        if (httpServletRequest.getParameter("gpuLength")!=null)
        {productRequest.setGpuLength(Double.parseDouble(httpServletRequest.getParameter("gpuLength")));}
        if (httpServletRequest.getParameter("capacity")!=null)
        {productRequest.setCapacity(Integer.parseInt(httpServletRequest.getParameter("capacity")));}
        if (httpServletRequest.getParameter("state")!=null)
        {productRequest.setState(Boolean.parseBoolean(httpServletRequest.getParameter("state")));}
        productRequest.setDescription(httpServletRequest.getParameter("description"));
        productRequest.setImageUrl(httpServletRequest.getParameter("imageUrl"));
        if(httpServletRequest.getParameter("stock")!=null)
        {productRequest.setStock(Integer.parseInt(httpServletRequest.getParameter("stock")));}
        if (httpServletRequest.getParameter("price")!=null)
        {productRequest.setPrice(Integer.parseInt(httpServletRequest.getParameter("price")));}

        if(product != null) {
            productService.updateProduct(productId, productRequest);
            Product updatedProduct = productService.getProductById(productId);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @DeleteMapping("/products/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam Integer productId){

        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    -------------------------------------------------------------------------
/* 暫時用不到
    @PostMapping("/products/CPU")
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid CpuRequest cpuRequest){
        Integer productId = productService.createProduct(cpuRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PostMapping("/products/DRAM")
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid DramRequest dramRequest){
        Integer productId = productService.createProduct(dramRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PostMapping("/products/MB")
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid MbRequest mbRequest){
        Integer productId = productService.createProduct(mbRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PostMapping("/products/GPU")
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid GpuRequest gpuRequest){
        Integer productId = productService.createProduct(gpuRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PostMapping("/products/COOLER")
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid CoolerRequest coolerRequest){
        Integer productId = productService.createProduct(coolerRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PostMapping("/products/STORAGE")
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid StorageRequest storageRequest){
        Integer productId = productService.createProduct(storageRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PostMapping("/products/POWER")
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid PowerRequest powerRequest){
        Integer productId = productService.createProduct(powerRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PostMapping("/products/CASE")
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid CaseRequest caseRequest){
        Integer productId = productService.createProduct(caseRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
*/
    @PostMapping("/products")
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
