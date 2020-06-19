package com.springboot.app.productos.controllers;

import com.springboot.app.productos.models.entity.Producto;
import com.springboot.app.productos.models.service.IProductoService;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
