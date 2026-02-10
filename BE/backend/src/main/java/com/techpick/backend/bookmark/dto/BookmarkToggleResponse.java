package com.techpick.backend.bookmark.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookmarkToggleResponse(String action, LocalDateTime createdAt) {
}
