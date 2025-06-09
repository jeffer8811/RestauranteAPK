document.addEventListener('DOMContentLoaded', function() {
    const sidebar = document.getElementById('sidebar');
    const sidebarToggle = document.getElementById('sidebarToggle');
    const mainContent = document.getElementById('mainContent');
    
    // Estado inicial (puedes cambiarlo a true si quieres que inicie colapsado)
    let sidebarCollapsed = localStorage.getItem('sidebarCollapsed') === 'true';
    
    // Aplicar estado inicial
    if (sidebarCollapsed) {
        sidebar.classList.add('collapsed');
        mainContent.classList.add('expanded');
    }
    
    // Función para alternar la barra lateral
    function toggleSidebar() {
        sidebarCollapsed = !sidebarCollapsed;
        sidebar.classList.toggle('collapsed');
        mainContent.classList.toggle('expanded');
        
        // Guardar estado en localStorage
        localStorage.setItem('sidebarCollapsed', sidebarCollapsed);
        
        // Opcional: Cambiar ícono según estado
        const icon = sidebarToggle.querySelector('i');
        if (sidebarCollapsed) {
            icon.classList.remove('fa-bars');
            icon.classList.add('fa-chevron-right');
        } else {
            icon.classList.remove('fa-chevron-right');
            icon.classList.add('fa-bars');
        }
    }
    
    // Event listener para el botón
    sidebarToggle.addEventListener('click', toggleSidebar);
    
    // Opcional: Cerrar barra al hacer clic fuera en móviles
    document.addEventListener('click', function(e) {
        if (window.innerWidth <= 768 && 
            !sidebar.contains(e.target) && 
            e.target !== sidebarToggle &&
            !sidebar.classList.contains('collapsed')) {
            toggleSidebar();
        }
    });
});