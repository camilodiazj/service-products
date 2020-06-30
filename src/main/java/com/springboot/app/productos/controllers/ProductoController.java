package com.springboot.app.productos.controllers;

import com.springboot.app.commons.models.entity.Producto;
import com.springboot.app.productos.models.service.IProductoService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ProductoController {

  private final IProductoService iProductoService;

  @Value("${server.port}")
  private Integer port;

  public ProductoController(IProductoService iProductoService) {
    this.iProductoService = iProductoService;
  }

  @GetMapping("/listar")
  public List<Producto> listar() {
    return iProductoService.findAll().stream()
        .peek(producto -> producto.setPort(port))
        .collect(Collectors.toList());
  }

  @GetMapping("/ver/{id}")
  public Producto detalle(@PathVariable Long id) throws Exception {
    Producto producto = iProductoService.findById(id);
    producto.setPort(port);
//    try {
//      Thread.sleep(2000L);
//    } catch (Exception ex) {
//      ex.printStackTrace();
//    }
    return producto;
  }

  @PostMapping("/crear")
  @ResponseStatus(HttpStatus.CREATED)
  public Producto crear(@RequestBody Producto producto){
      return iProductoService.save(producto);
  }

  @PutMapping("/editar/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public Producto editar(@RequestBody Producto producto, @PathVariable Long id){
    Producto product = iProductoService.findById(id);
    product.setNombre(producto.getNombre());
    product.setPrecio(producto.getPrecio());
    return iProductoService.save(producto);
  }

  @DeleteMapping("/eliminar/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id){
    iProductoService.deleteById(id);
  }

}
