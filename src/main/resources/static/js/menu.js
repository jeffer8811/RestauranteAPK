document.addEventListener('DOMContentLoaded', function () {
    let currentItemId = null;

    const addItemBtn = document.getElementById('addItemBtn');
    const itemModal = document.getElementById('itemModal');
    const confirmModal = document.getElementById('confirmModal');
    const closeModalButtons = document.querySelectorAll('.close, .btn-cancel');
    const itemForm = document.getElementById('itemForm');
    const confirmDeleteBtn = document.getElementById('confirmDelete');
    const cancelDeleteBtn = document.getElementById('cancelDelete');
    const tabButtons = document.querySelectorAll('.tab-btn');
    const itemImageInput = document.getElementById('itemImage');
    const imagePreview = document.getElementById('imagePreview');

    function openItemModal(itemId = null) {
        currentItemId = itemId;

        if (itemId) {
            document.getElementById('modalTitle').textContent = 'Editar ítem del menú';
            const itemRow = document.querySelector(`.btn-edit[data-id="${itemId}"]`).closest('tr');

            document.getElementById('itemId').value = itemId;
            document.getElementById('itemName').value = itemRow.cells[1].textContent;
            document.getElementById('itemDescription').value = itemRow.cells[2].textContent;
            document.getElementById('itemCategory').value = itemRow.cells[3].textContent.toLowerCase();
            document.getElementById('itemPrice').value = parseFloat(itemRow.cells[4].textContent.replace('$', ''));
            document.getElementById('itemAvailable').checked = itemRow.cells[5].querySelector('input').checked;

            const imgElement = itemRow.cells[0].querySelector('img');
            if (imgElement) {
                imagePreview.innerHTML = `<img src="${imgElement.src}" alt="Preview">`;
            } else {
                imagePreview.innerHTML = '<i class="fas fa-image"></i>';
            }
        } else {
            document.getElementById('modalTitle').textContent = 'Agregar nuevo ítem al menú';
            itemForm.reset();
            imagePreview.innerHTML = '<i class="fas fa-image"></i>';
        }

        itemModal.style.display = 'block';
    }

    function openConfirmModal(itemId) {
        currentItemId = itemId;
        confirmModal.style.display = 'block';
    }

    function closeModals() {
        itemModal.style.display = 'none';
        confirmModal.style.display = 'none';
    }

    function filterTableByCategory(category) {
        const rows = document.querySelectorAll('#menuTable tbody tr');

            rows.forEach(row => {
        const rowCategory = row.cells[3].textContent.trim().toLowerCase();
        if (category === 'all' || rowCategory === category) {
        row.style.display = '';
         } else {
        row.style.display = 'none';
            }
});

    }

    addItemBtn.addEventListener('click', () => openItemModal());

    closeModalButtons.forEach(button => {
        button.addEventListener('click', closeModals);
    });

    document.querySelectorAll('.btn-edit').forEach(button => {
        button.addEventListener('click', (e) => {
            const itemId = e.currentTarget.getAttribute('data-id');
            openItemModal(itemId);
        });
    });

    document.querySelectorAll('.btn-delete').forEach(button => {
        button.addEventListener('click', (e) => {
            const itemId = e.currentTarget.getAttribute('data-id');
            openConfirmModal(itemId);
        });
    });

confirmDeleteBtn.addEventListener('click', () => {
    fetch(`/menu/delete/${currentItemId}`)
        .then(response => {
            if (response.ok) {
                // Eliminar la fila de la tabla
                const itemRow = document.querySelector(`.btn-delete[data-id="${currentItemId}"]`).closest('tr');
                itemRow.remove();
                showToast('Ítem eliminado correctamente', 'success');
            } else {
                showToast('Error al eliminar el ítem', 'error');
            }
            closeModals();
        })
        .catch(error => {
            console.error("Error al eliminar:", error);
            showToast('Error de red', 'error');
            closeModals();
        });
});


    cancelDeleteBtn.addEventListener('click', closeModals);

    tabButtons.forEach(button => {
        button.addEventListener('click', () => {
            tabButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');

            const category = button.getAttribute('data-category');
            filterTableByCategory(category);
        });
    });

    itemImageInput.addEventListener('change', function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();

            reader.onload = function (e) {
                imagePreview.innerHTML = `<img src="${e.target.result}" alt="Preview">`;
            }

            reader.readAsDataURL(file);
        }
    });

    window.addEventListener('click', (e) => {
        if (e.target === itemModal || e.target === confirmModal) {
            closeModals();
        }
    });

    function showToast(message, type = 'success') {
        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;
        toast.textContent = message;
        document.body.appendChild(toast);

        setTimeout(() => {
            toast.classList.add('show');
        }, 100);

        setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => {
                toast.remove();
            }, 300);
        }, 3000);
    }

    // ✅ Mostrar todos al inicio
    filterTableByCategory('all');
});
