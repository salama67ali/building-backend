package com.suzasob.building_permission.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User roles available in the building permissions system")
public enum UserRole {
    @Schema(description = "System administrator with full access")
    ADMIN,
    @Schema(description = "Engineering professional who reviews technical aspects")
    ENGINEER,
    @Schema(description = "External consultant providing specialized services")
    CONSULTANT,
    @Schema(description = "Property owner applying for permits")
    OWNER,
    @Schema(description = "Board member involved in approval processes")
    BOARD
}