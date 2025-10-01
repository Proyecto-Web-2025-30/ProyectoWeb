// ===================================
// UTILIDADES GENERALES
// ===================================

document.addEventListener('DOMContentLoaded', function() {
    
    // Auto-hide alerts después de 5 segundos
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
            alert.style.opacity = '0';
            alert.style.transform = 'translateY(-10px)';
            setTimeout(() => alert.remove(), 500);
        }, 5000);
    });

    // Validación de formularios mejorada
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const submitBtn = form.querySelector('button[type="submit"]');
            if (submitBtn && !submitBtn.disabled) {
                submitBtn.disabled = true;
                submitBtn.style.opacity = '0.7';
                submitBtn.innerHTML = '<span class="spinner"></span> Procesando...';
                
                // Re-habilitar después de 3 segundos por seguridad
                setTimeout(() => {
                    submitBtn.disabled = false;
                    submitBtn.style.opacity = '1';
                }, 3000);
            }
        });
    });

    // Añadir efecto de focus a inputs
    const inputs = document.querySelectorAll('input, select, textarea');
    inputs.forEach(input => {
        input.addEventListener('focus', function() {
            this.parentElement.classList.add('focused');
        });
        
        input.addEventListener('blur', function() {
            this.parentElement.classList.remove('focused');
        });
    });

    // Mostrar/ocultar contraseña
    const passwordInputs = document.querySelectorAll('input[type="password"]');
    passwordInputs.forEach(input => {
        const wrapper = input.parentElement;
        const toggleBtn = document.createElement('button');
        toggleBtn.type = 'button';
        toggleBtn.innerHTML = '👁️';
        toggleBtn.style.cssText = `
            position: absolute;
            right: 12px;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            cursor: pointer;
            font-size: 1.2rem;
            opacity: 0.6;
            transition: opacity 0.3s;
            padding: 4px;
        `;
        toggleBtn.addEventListener('mouseenter', () => toggleBtn.style.opacity = '1');
        toggleBtn.addEventListener('mouseleave', () => toggleBtn.style.opacity = '0.6');
        
        // Solo agregar si el input tiene label (para evitar duplicados)
        if (input.id && document.querySelector(`label[for="${input.id}"]`)) {
            wrapper.style.position = 'relative';
            wrapper.appendChild(toggleBtn);
            
            toggleBtn.addEventListener('click', function() {
                if (input.type === 'password') {
                    input.type = 'text';
                    toggleBtn.innerHTML = '🙈';
                } else {
                    input.type = 'password';
                    toggleBtn.innerHTML = '👁️';
                }
            });
        }
    });

    // Validación de fuerza de contraseña
    const passwordField = document.querySelector('input[name="password"], input[name="adminPassword"]');
    if (passwordField) {
        const strengthIndicator = document.createElement('div');
        strengthIndicator.style.cssText = `
            height: 4px;
            background: #E0E0E0;
            border-radius: 2px;
            margin-top: 8px;
            overflow: hidden;
        `;
        
        const strengthBar = document.createElement('div');
        strengthBar.style.cssText = `
            height: 100%;
            width: 0%;
            transition: all 0.3s ease;
            border-radius: 2px;
        `;
        strengthIndicator.appendChild(strengthBar);
        
        const strengthText = document.createElement('small');
        strengthText.style.cssText = `
            display: block;
            margin-top: 4px;
            font-size: 0.8rem;
            color: var(--color-text-light);
        `;
        
        passwordField.parentElement.appendChild(strengthIndicator);
        passwordField.parentElement.appendChild(strengthText);
        
        passwordField.addEventListener('input', function() {
            const password = this.value;
            let strength = 0;
            
            if (password.length >= 8) strength += 25;
            if (password.length >= 12) strength += 15;
            if (/[a-z]/.test(password)) strength += 15;
            if (/[A-Z]/.test(password)) strength += 15;
            if (/[0-9]/.test(password)) strength += 15;
            if (/[^a-zA-Z0-9]/.test(password)) strength += 15;
            
            strengthBar.style.width = strength + '%';
            
            if (strength < 40) {
                strengthBar.style.background = '#EF5350';
                strengthText.textContent = '❌ Contraseña débil';
                strengthText.style.color = '#EF5350';
            } else if (strength < 70) {
                strengthBar.style.background = '#FFA726';
                strengthText.textContent = '⚠️ Contraseña media';
                strengthText.style.color = '#FFA726';
            } else {
                strengthBar.style.background = '#66BB6A';
                strengthText.textContent = '✓ Contraseña fuerte';
                strengthText.style.color = '#66BB6A';
            }
            
            if (password.length === 0) {
                strengthText.textContent = '';
                strengthBar.style.width = '0%';
            }
        });
    }

    // Animación de entrada para tarjetas
    const cards = document.querySelectorAll('.card, .dashboard-card');
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };
    
    const observer = new IntersectionObserver(function(entries) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '0';
                entry.target.style.transform = 'translateY(20px)';
                
                setTimeout(() => {
                    entry.target.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
                    entry.target.style.opacity = '1';
                    entry.target.style.transform = 'translateY(0)';
                }, 100);
                
                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);
    
    cards.forEach(card => observer.observe(card));

    // Confirmación para acciones críticas (como cerrar sesión)
    const logoutForms = document.querySelectorAll('form[action*="logout"]');
    logoutForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!confirm('¿Estás seguro que deseas cerrar sesión?')) {
                e.preventDefault();
            }
        });
    });

    // Mejorar selects con animación
    const selects = document.querySelectorAll('select');
    selects.forEach(select => {
        select.addEventListener('change', function() {
            this.style.transform = 'scale(1.02)';
            setTimeout(() => {
                this.style.transform = 'scale(1)';
            }, 200);
        });
    });

    // Agregar ripple effect a botones
    const buttons = document.querySelectorAll('button, .btn');
    buttons.forEach(button => {
        button.addEventListener('click', function(e) {
            const ripple = document.createElement('span');
            const rect = this.getBoundingClientRect();
            const size = Math.max(rect.width, rect.height);
            const x = e.clientX - rect.left - size / 2;
            const y = e.clientY - rect.top - size / 2;
            
            ripple.style.cssText = `
                position: absolute;
                width: ${size}px;
                height: ${size}px;
                border-radius: 50%;
                background: rgba(255, 255, 255, 0.6);
                left: ${x}px;
                top: ${y}px;
                transform: scale(0);
                animation: ripple 0.6s ease-out;
                pointer-events: none;
            `;
            
            this.style.position = 'relative';
            this.style.overflow = 'hidden';
            this.appendChild(ripple);
            
            setTimeout(() => ripple.remove(), 600);
        });
    });

    // Agregar animación de ripple
    const style = document.createElement('style');
    style.textContent = `
        @keyframes ripple {
            to {
                transform: scale(4);
                opacity: 0;
            }
        }
        
        .focused {
            transform: translateX(2px);
            transition: transform 0.2s ease;
        }
    `;
    document.head.appendChild(style);

    console.log('✨ Process Manager inicializado correctamente');
});

// Función para mostrar notificaciones personalizadas
function showNotification(message, type = 'info') {
    const notification = document.createElement('div');
    notification.className = `alert alert-${type}`;
    notification.textContent = message;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        z-index: 10000;
        min-width: 300px;
        animation: slideIn 0.3s ease-out;
    `;
    
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.style.animation = 'slideOut 0.3s ease-out';
        setTimeout(() => notification.remove(), 300);
    }, 3000);
}

// Agregar animaciones de notificaciones
const notifStyle = document.createElement('style');
notifStyle.textContent = `
    @keyframes slideIn {
        from {
            transform: translateX(400px);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    @keyframes slideOut {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(400px);
            opacity: 0;
        }
    }
`;
document.head.appendChild(notifStyle);
