package com.huuduc.veladstore.service.impl;

import com.huuduc.veladstore.data.dto.PaginationDTO;
import com.huuduc.veladstore.data.dto.product.ProductDTORequest;
import com.huuduc.veladstore.data.dto.product.ProductDTOResponse;
import com.huuduc.veladstore.data.entity.Category;
import com.huuduc.veladstore.data.entity.Image;
import com.huuduc.veladstore.data.entity.Product;
import com.huuduc.veladstore.data.entity.Type;
import com.huuduc.veladstore.data.mapper.ImageMapper;
import com.huuduc.veladstore.data.mapper.ProductMapper;
import com.huuduc.veladstore.exception.ConflicExeception;
import com.huuduc.veladstore.exception.NotFoundException;
import com.huuduc.veladstore.exception.NullException;
import com.huuduc.veladstore.repository.CategoryRepository;
import com.huuduc.veladstore.repository.ImageRepository;
import com.huuduc.veladstore.repository.ProductRepository;
import com.huuduc.veladstore.repository.TypeRepository;
import com.huuduc.veladstore.service.FileService;
import com.huuduc.veladstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private FileService fileService;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public ProductDTOResponse create(ProductDTORequest productDTORequest, List<MultipartFile> fileImages) {

        Map<String, Object> errors = new HashMap<>();

        //Check null param
        if (productDTORequest == null || ObjectUtils.isEmpty(productDTORequest)) {
            throw new NullException(Collections.singletonMap("Product Request", null));
        }
        if (productDTORequest.getName() == null || ObjectUtils.isEmpty(productDTORequest.getName())) {
            throw new NullException(Collections.singletonMap("Product name", null));
        }
        if (ObjectUtils.isEmpty(productDTORequest.getPrice()) || productDTORequest.getPrice() == 0) {
            throw new NullException(Collections.singletonMap("Price", null));
        }

        //Get new product from request
        Product newProduct = this.productMapper.toEntity(productDTORequest);

        //Check if name is exits then throw new conflic exception
        if (this.productRepository.existsByName(productDTORequest.getName())) {
            throw new ConflicExeception(Collections.singletonMap("Name is exits", productDTORequest.getName()));
        }

        //Set status auto true when new create
        newProduct.setStatus(true);

        //Set and check category if category not exits then throw not found exception
        newProduct.setCategory(this.categoryRepository.findByName(productDTORequest.getCategoryName()).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Category not exit", productDTORequest.getCategoryName()))
        ));

        //Set and check type if category not exits then throw not found exception
        newProduct.setType(this.typeRepository.findByName(productDTORequest.getTypeName()).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Type not exit", productDTORequest.getTypeName()))
        ));

        //Upload List img to /path/file
        List<Image> imageList = new ArrayList<>();
        if (fileImages != null) {
            fileImages.forEach(file -> {
                String fileName = this.fileService.uploadFile(file);

                if (fileName == null) {
                    errors.put("Image not be null: ", productDTORequest.getName());
                }

                Image image = new Image();
                image.setName(fileName);
                image.setStatus(true);
                image.setProduct(newProduct);
                imageList.add(image);

            });
        } else {
            errors.put("Images not be null!", productDTORequest.getName());
        }

        //Check if img request null then throw new exception
        if (errors.size() > 0) {
            throw new NotFoundException(Collections.singletonMap("Param is empty:", errors));
        }

        newProduct.setImages(imageList);

        //Save product to database
        this.productRepository.save(newProduct);

        return this.productMapper.toDTO(newProduct);
    }

    @Override
    public ProductDTOResponse update(long id, ProductDTORequest productDTORequest, List<MultipartFile> fileImages) {

        Map<String, Object> errors = new HashMap<>();

        //Check product with id request had exit
        Product findProduct = this.productRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Product id", id))
        );

        //Check if product had been soft deleted
        if (!findProduct.isStatus()) {
            throw new NotFoundException(Collections.singletonMap("Product id", id));
        }

        //Check null param
        if (productDTORequest == null || ObjectUtils.isEmpty(productDTORequest)) {
            throw new NullException(Collections.singletonMap("Product Request", null));
        }
        if (productDTORequest.getName() == null || ObjectUtils.isEmpty(productDTORequest.getName())) {
            throw new NullException(Collections.singletonMap("Product name", null));
        }
        if (ObjectUtils.isEmpty(productDTORequest.getPrice()) || productDTORequest.getPrice() == 0) {
            throw new NullException(Collections.singletonMap("Price", null));
        }

        //Get new product from request
        Product newProduct = this.productMapper.toEntity(productDTORequest);

        //Set status auto true when new create
        newProduct.setStatus(true);

        //Set and check category if category not exits then throw not found exception
        newProduct.setCategory(this.categoryRepository.findByName(productDTORequest.getCategoryName()).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Category not exit", productDTORequest.getCategoryName()))
        ));

        //Set and check type if category not exits then throw not found exception
        newProduct.setType(this.typeRepository.findByName(productDTORequest.getTypeName()).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Type not exit", productDTORequest.getTypeName()))
        ));

        if (!findProduct.getName().equals(newProduct.getName())) {

            if (this.productRepository.existsByName(newProduct.getName())) {
                throw new ConflicExeception(Collections.singletonMap("Product name", newProduct.getName()));
            }
        }

        //Update newProduct with oldProduct's id (findProduct)
        newProduct.setId(id);

        //Upload List img to /path/file
        List<Image> imageList = findProduct.getImages();
        if (fileImages != null) {

            //Soft delete old image
            imageList.forEach(image -> {
                image.setStatus(false);
            });

            fileImages.forEach(file -> {

                String fileName = this.fileService.uploadFile(file);

                if (fileName == null) {
                    errors.put("Image not be null: ", productDTORequest.getName());
                }

                Image image = new Image();
                image.setName(fileName);
                image.setStatus(true);
                image.setProduct(newProduct);
                imageList.add(image);

            });

        } else {
            newProduct.setImages(findProduct.getImages());
        }

        //Check if img request null then throw new exception
        if (errors.size() > 0) {
            throw new NotFoundException(Collections.singletonMap("Param is empty:", errors));
        }

        newProduct.setImages(imageList);

        //Save product to database
        this.productRepository.save(newProduct);

        return this.productMapper.toDTO(newProduct);
    }

    @Override
    public PaginationDTO findByCategory(long id, int no, int limit) {

        //Check null param
        if (ObjectUtils.isEmpty(id)) {
            throw new NullException(Collections.singletonMap("id", null));
        }

        //Check exits category
        Category category = this.categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Category id", id))
        );

        if (!category.isStatus()) {
            throw new NotFoundException(Collections.singletonMap("Category id", id));
        }

        Page<ProductDTOResponse> page = this.productRepository.findByCategoryIdAndStatusIsTrue(id, PageRequest.of(no, limit))
                .map(p -> this.productMapper.toDTO(p));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(),
                page.getTotalElements(), page.getSize(), page.getNumber());

    }

    @Override
    public PaginationDTO findByType(long id, int no, int limit) {

        //Check null param
        if (ObjectUtils.isEmpty(id)) {
            throw new NullException(Collections.singletonMap("id", null));
        }

        //Check exits type
        Type type = this.typeRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Type id", id))
        );

        if (!type.isStatus()) {
            throw new NotFoundException(Collections.singletonMap("Type id", id));
        }

        Page<ProductDTOResponse> page = this.productRepository.findByTypeIdAndStatusIsTrue(id, PageRequest.of(no, limit))
                .map(p -> this.productMapper.toDTO(p));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(),
                page.getTotalElements(), page.getSize(), page.getNumber());
    }

    @Override
    public PaginationDTO getAll(int no, int limit) {

        //Get all product and set on page with products not be deleted
        Page<ProductDTOResponse> page = this.productRepository.findByStatusIsTrue(PageRequest.of(no, limit))
                .map(p -> this.productMapper.toDTO(p));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(),
                page.getTotalElements(), page.getSize(), page.getNumber());
    }

    @Override
    public boolean deleteById(Long id) {

        //Check null PathVariable
        if (id == null) {
            throw new NullException(Collections.singletonMap("id", null));
        }

        //Check database have any product with id on request
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("id", id))
        );

        //Check if the product has been removed
        if (!product.isStatus()) {
            throw new NotFoundException(Collections.singletonMap("id", id));
        }

        product.setStatus(false);
        productRepository.save(product);

        return true;
    }

    @Override
    public ProductDTOResponse findById(Long id) {

        //Find product from database if not exits then throw exception
        Product findProduct = this.productRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("id", id))
        );

        //Check if product has been deleted then throw exception
        if (!findProduct.isStatus()) {
            throw new NotFoundException(Collections.singletonMap("id", id));
        }

        return this.productMapper.toDTO(findProduct);
    }

    @Override
    public List<ProductDTOResponse> findByName(String name) {

        //Check null param
        if (name == null || ObjectUtils.isEmpty(name)) {
            throw new NullException(Collections.singletonMap("name", null));
        }

        //List product have containing name
        List<Product> products = this.productRepository.findByNameContaining(name);


        //Check list have product is deleted
        for (int i = 0; i < products.size(); i++) {
            if (!products.get(i).isStatus()) {
                products.remove(products.get(i));
            }
        }

        if (products.size() == 0) {
            throw new NotFoundException(Collections.singletonMap("name", name));
        }
        List<ProductDTOResponse> productDTOResponseList = products.stream()
                .map(p -> this.productMapper.toDTO(p)).toList();

        return productDTOResponseList;
    }


}
