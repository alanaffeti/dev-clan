<?php

namespace App\Controller;
use App\Entity\User;
use App\Form\UserType;
use App\Form\BlockUserType;
use App\Form\SearchType;
use App\Exception\UserNotFoundException;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\UserRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Security\Core\User\PasswordAuthenticatedUserInterface;
use Symfony\Component\Security\Core\User\PasswordUpgraderInterface;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

class UserController extends AbstractController
{
    public function __constructUser(ManagerRegistry $registry)
    {
        parent::__construct($registry, User::class);
    }

    private $passwordEncoder;

    public function __construct(UserPasswordEncoderInterface $passwordEncoder)
    {
        $this->passwordEncoder = $passwordEncoder;
    }

    
       
      #[Route("/registrationl", name:"registrationl")]
     
    public function listUsers()
    {
        $userRepository = $this->getDoctrine()->getRepository(User::class);
        $user = $userRepository->findAll();

        return $this->render('user/index.html.twig', ['user' => $user]);
    }



    #[Route('/{id}/edit', name: 'app_user_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, User $user, UserRepository $userRepository): Response
    {
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $user->setPassword($this->passwordEncoder->encodePassword($user, $user->getPassword()));
            $userRepository->save($user, true);

            return $this->redirectToRoute('registrationl', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('user/updateUser.html.twig', [
            'user' => $user,
            'f' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_user_delete', methods: ['GET' , 'POST'])]
    public function delete(Request $request, User $user, UserRepository $userRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$user->getId(), $request->request->get('_token'))) {
            $userRepository->remove($user, true);
        }

        return $this->redirectToRoute('registrationl', [], Response::HTTP_SEE_OTHER);
    }

//  #[Route('/profile/{id}', name: 'user_profile_edit', methods: ['GET', 'POST'])]
//     public function editUserProfile(Request $request, user $user, UserRepository $UserRepository): Response
//     {
//         $this->denyAccessUnlessGranted('ROLE_USER');
//         $form = $this->createForm(UserType::class, $user);
//         $form->handleRequest($request);

//         if ($form->isSubmitted()) {
//             $UserRepository->save($user, true);

//             return $this->redirectToRoute('user_profile', [], Response::HTTP_SEE_OTHER);
//         }

//         return $this->renderForm('profile_edit.html.twig', [
//             'user' => $user,
//             'form' => $form,
//         ]);
//     }

   
   
     
    
 
   
  
}
