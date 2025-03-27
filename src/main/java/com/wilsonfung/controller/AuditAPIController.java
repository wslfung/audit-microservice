package com.wilsonfung.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wilsonfung.model.LogMessage;
import com.wilsonfung.service.AuditLogService;

@RestController
@RequestMapping("/api")
public class AuditAPIController {
    private static final Logger logger = LoggerFactory.getLogger(AuditAPIController.class);
    
    @Autowired
    private AuditLogService auditLogService;

    // @GetMapping("/log-messages")
    // public List<LogMessage> retrieveLogMessages(@RequestParam(defaultValue = "10") int count, Authentication authentication) {
    //     Saml2Authentication saml2Auth = (Saml2Authentication) authentication;
    //     String userId = saml2Auth.getName();
    //     boolean isAdmin = saml2Auth.getAuthorities().stream()
    //         .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        
    //     logger.info("Retrieving {} log messages for user {}, isAdmin: {}", count, userId, isAdmin);
    //     return auditLogService.retrieveLogMessages(isAdmin, userId, count);
    // }

    @GetMapping("/log-messages")
    public List<LogMessage> retrieveLogMessages(
            @RequestParam(defaultValue = "10") int count,
            @RequestHeader("Authorization") String authorizationHeader) {
        
        // Extract the SAML token from the Authorization header
        String token = authorizationHeader.replace("SAML ", "");
        
        // Parse the SAML token
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Decode the Base64 token
            byte[] decodedBytes = Base64.getDecoder().decode(token);
            String decodedToken = new String(decodedBytes, StandardCharsets.UTF_8);
            
            logger.debug("Decoded SAML token: {}", decodedToken);
            
            // Parse the XML
            Document document = builder.parse(new InputSource(new ByteArrayInputStream(decodedToken.getBytes(StandardCharsets.UTF_8))));
            
            // Set up namespace context
            String samlNamespace = "urn:oasis:names:tc:SAML:2.0:assertion";
            
            // Extract NameID
            NodeList nameIdNodes = document.getElementsByTagNameNS(samlNamespace, "NameID");
            logger.debug("Found {} NameID nodes", nameIdNodes.getLength());
            String userId = nameIdNodes.getLength() > 0 
                ? nameIdNodes.item(0).getTextContent().trim()
                : "unknown";
            
            // Extract UserRole attribute
            NodeList attributeNodes = document.getElementsByTagNameNS(samlNamespace, "Attribute");
            logger.debug("Found {} Attribute nodes", attributeNodes.getLength());
            boolean isAdmin = false;
            
            for (int i = 0; i < attributeNodes.getLength(); i++) {
                Node attributeNode = attributeNodes.item(i);
                if (attributeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element attributeElement = (Element) attributeNode;
                    String attributeName = attributeElement.getAttribute("Name");
                    logger.debug("Processing attribute: {}", attributeName);
                    if ("UserRole".equals(attributeName)) {
                        NodeList attributeValues = attributeElement.getElementsByTagNameNS(samlNamespace, "AttributeValue");
                        if (attributeValues.getLength() > 0) {
                            String role = attributeValues.item(0).getTextContent().trim();
                            logger.debug("Found UserRole value: {}", role);
                            isAdmin = "Admin".equalsIgnoreCase(role);
                            break;
                        }
                    }
                }
            }
            
            logger.info("Retrieving {} log messages for user {}, isAdmin: {}", count, userId, isAdmin);
            return auditLogService.retrieveLogMessages(isAdmin, userId, count);
            
        } catch (Exception e) {
            logger.error("Error parsing SAML token: {}", e.getMessage(), e);
            throw new RuntimeException("Invalid SAML token", e);
        }
    }
}
