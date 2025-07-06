
function updateDateTime() {
    const now = new Date();
    
   
    const optionsDate = { 
        weekday: 'long', 
        year: 'numeric', 
        month: 'long', 
        day: 'numeric',
        timeZone: 'America/Mexico_City' 
    };
    
    document.getElementById('currentDate').textContent = now.toLocaleDateString('es-ES', optionsDate);

    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    document.getElementById('currentTime').textContent = `${hours}:${minutes}`;
}


function initSalesChart() {
    const ctx = document.getElementById('salesChart').getContext('2d');
    
  
    const salesData = {
        labels: ['Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb', 'Dom'],
        datasets: [{
            label: 'Ventas ($)',
            data: [1200, 1900, 1500, 2000, 1800, 2500, 2200],
            backgroundColor: 'rgba(78, 115, 223, 0.7)',
            borderColor: 'rgba(78, 115, 223, 1)',
            borderWidth: 1
        }]
    };
    
  
    const chartOptions = {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true,
                ticks: {
                    callback: function(value) {
                        return '$' + value;
                    }
                }
            }
        },
        plugins: {
            tooltip: {
                callbacks: {
                    label: function(context) {
                        return 'Ventas: $' + context.parsed.y;
                    }
                }
            }
        }
    };
    
    new Chart(ctx, {
        type: 'bar',
        data: salesData,
        options: chartOptions
    });
}


document.addEventListener('DOMContentLoaded', function() {
    // Actualizar fecha y hora inmediatamente y cada minuto
    updateDateTime();
    setInterval(updateDateTime, 60000);
    

    initSalesChart();
    

});